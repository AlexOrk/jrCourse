package jr_course.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jr_course.entity.Note;
import jr_course.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

@RestController
@RequestMapping("/notes")
public class NoteController {

    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    private NoteService noteService;

    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping("/")
    public List<Note> showNotes(@RequestParam("userId") int userId) {
        logger.info("\"/personal/notes?userId=" + userId + "\"");

        return noteService.findAllByUser_Id(userId);
    }

    @PostMapping("/save")
    public Note saveNote(@RequestBody String body) {
        logger.info("\"/personal/saveNote\"");

        ObjectMapper mapper = new ObjectMapper();
        StringReader reader = new StringReader(body);

        Note note = null;
        try {
            note = mapper.readValue(reader, Note.class);
            logger.info("Note was read.");
        } catch (IOException e) {
            logger.debug(e.getMessage());
            e.printStackTrace();
        }
        noteService.save(note);
        logger.info("Note was saved!");
        return note;
    }

    @DeleteMapping("/delete")
    public List<Note> deleteNote(@RequestParam("noteId") int noteId, @RequestParam("userId") int userId) {
        logger.info("\"/notes/deleteNote?noteId=" + noteId + "&userId=" + userId + "\"");

        noteService.deleteById(noteId);

        logger.info("Note was deleted!");
        logger.info("Return all notes.");
        return noteService.findAllByUser_Id(userId);
    }
}
/*
Показать заметки +
Создать заметку +
Изменить заметку +
Удалить заметку +
*/