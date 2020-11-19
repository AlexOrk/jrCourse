package jr_course.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import jr_course.entity.Note;
import jr_course.entity.User;
import jr_course.exception.main.CustomDataException;
import jr_course.service.*;
import jr_course.service.mq.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jr_course.exception.*;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

@RestController
@RequestMapping(value = "/notes", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
public class NoteController {

    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    private NoteService noteService;
    private UserService userService;
    private Producer producer;

    @Autowired
    public NoteController(NoteService noteService, UserService userService, Producer producer) {
        this.noteService = noteService;
        this.userService = userService;
        this.producer = producer;
    }

    @GetMapping()
    @ApiOperation(value = "Find all notes", notes = "Find all notes by user id", response = List.class)
    public List<Note> findNotes(@ApiParam(value = "Id value for user whose notes you need to find", required = true)
                                @RequestParam("userId") int userId) {
        logger.info("\"/notes/?userId=" + userId + "\"");

        List<Note> noteList = noteService.findAllByUserId(userId);
        producer.sendMessage(noteList, "Note");
        return noteList;
    }

    @PostMapping(value = "/save", consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Save a note", notes = "Save user note", response = Note.class)
    public Note saveNote(@RequestBody Note note,
                         @ApiParam(value = "Id value for user whose note you need to save", required = true)
                         @RequestParam("userId") int userId) {
        logger.info("\"/notes/saveNote?userId=" + userId + "\"");

        User user = userService.findById(userId);
        note.setUser(user);
        noteService.save(note);
        logger.info("Note was saved!");
        return note;
    }

    @DeleteMapping("/delete")
    @ApiOperation(value = "Delete note",
            notes = "Delete user note by id and return all user notes", response = List.class)
    public List<Note> deleteNote(@RequestParam("noteId") int noteId,
                                 @ApiParam(value = "Id value for user whose note you need to delete", required = true)
                                 @RequestParam("userId") int userId) {
        logger.info("\"/notes/deleteNote?noteId=" + noteId + "&userId=" + userId + "\"");

        noteService.deleteById(noteId);

        logger.info("Return all notes.");
        return noteService.findAllByUserId(userId);
    }

    @ExceptionHandler
    public ResponseEntity<DataErrorResponse> handleException(CustomDataException exception) {

        DataErrorResponse response = new DataErrorResponse();
        response.setStatus(exception.getStatus().value());
        response.setMessage(exception.getMessage());
        response.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<DataErrorResponse>(response, exception.getStatus());
    }
}