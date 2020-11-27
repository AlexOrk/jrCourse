package jr_course.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import jr_course.entity.*;
import jr_course.service.*;
import jr_course.service.mq.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/mq", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
public class MqController {
    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    private Producer producer;
    private GrammarService grammarService;
    private UserService userService;

    @Autowired
    private MqController(Producer producer, GrammarService grammarService, UserService userService) {
        this.producer = producer;
        this.grammarService = grammarService;
        this.userService = userService;
    }

    @PostMapping(value = "/saveExercise", consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Save exercise", notes = "Save exercise to the exercise list", response = Exercise.class)
    public ResponseEntity saveExercise(@Valid @RequestBody Exercise exercise,
                                 @ApiParam(value = "Id value for grammar you need to save", required = true)
                                 @RequestParam("grammarId") int grammarId) {
        logger.info("\"/exercises/saveExercise?grammarId=" + grammarId + "\"");

        Grammar grammar = grammarService.findById(grammarId);
        exercise.setGrammar(grammar);
        producer.sendMessage(exercise);

        return new ResponseEntity("Request received and processing", HttpStatus.OK);
    }

    @PostMapping(value = "/saveNote", consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Save note", notes = "Save note to the note list", response = Note.class)
    public ResponseEntity saveNote(@Valid @RequestBody Note note,
                         @ApiParam(value = "Id value for user whose note you need to save", required = true)
                         @RequestParam("userId") int userId) {
        logger.info("\"/notes/saveNote?userId=" + userId + "\"");

        User user = userService.findById(userId);
        note.setUser(user);
        producer.sendMessage(note);

        return new ResponseEntity("Request received and processing", HttpStatus.OK);
    }

    @PostMapping(value = "/saveWord", consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Save word", notes = "Save word to the word list", response = Word.class)
    public ResponseEntity saveWord(@RequestBody Word word) {
        logger.info("\"/mq/saveWord\"");

        producer.sendMessage(word);

        return new ResponseEntity("Request received and processing", HttpStatus.OK);
    }

    @PostMapping(value = "/saveGrammar", consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Save grammar", notes = "Save grammar to the grammar list", response = Grammar.class)
    public ResponseEntity saveGrammar(@Valid @RequestBody Grammar grammar) {
        logger.info("\"/mq/saveGrammar\"");

        producer.sendMessage(grammar);

        return new ResponseEntity("Request received and processing", HttpStatus.OK);
    }
}
