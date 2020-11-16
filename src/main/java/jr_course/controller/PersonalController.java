package jr_course.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import jr_course.entity.Grammar;
import jr_course.entity.User;
import jr_course.entity.Word;
import jr_course.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/personal", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
public class PersonalController {
    // Controller for personal users section

    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    private UserService userService;
    private WordService wordService;
    private GrammarService grammarService;

    @Autowired
    public PersonalController(UserService userService, WordService wordService,
                              GrammarService grammarService) {
        this.userService = userService;
        this.wordService = wordService;
        this.grammarService = grammarService;
    }

    @GetMapping("/words")
    @ApiOperation(value = "Show all words", notes = "Find and return all words in the user's vocabulary by user id",
            response = List.class)
    public List<Word> showWords(@ApiParam(value = "Id value for user whose words you need to find", required = true)
                                @RequestParam("userId") int userId) {
        logger.info("\"/personal/words?userId=" + userId + "\"");

        return wordService.findAllByUserId(userId);
    }

    @GetMapping("/grammar")
    @ApiOperation(value = "Show all grammar", notes = "Find and return all grammar in the user's list by user id",
            response = List.class)
    public List<Grammar> showGrammar(@ApiParam(value = "Id value for user whose grammar you need to find", required = true)
                                     @RequestParam("userId") int userId) {
        logger.info("\"/personal/grammar?userId=" + userId + "\"");

        return grammarService.findAllByUserId(userId);
    }

    @DeleteMapping("/delete/word")
    @ApiOperation(value = "Delete word",
            notes = "Delete word by id from user's personal vocabulary and return all words", response = List.class)
    public List<Word> deleteWord(@ApiParam(value = "Id value for word you need to delete", required = true)
                                 @RequestParam("wordId") int wordId,
                                 @ApiParam(value = "Id value for user to delete the word", required = true)
                                 @RequestParam("userId") int userId) {
        logger.info("\"deleteWordFromPersonal?wordId=" + wordId + "&userId=" + userId + "\"");
        logger.info("Delete word from personal vocabulary.");

        logger.info("Find user by id.");
        User user = userService.findById(userId);

        wordService.deleteWordFromPersonalVocabulary(wordId, user);

        logger.info("Return all words by user id.");
        return wordService.findAllByUserId(userId);
    }

    @DeleteMapping("/delete/grammar")
    @ApiOperation(value = "Delete grammar",
            notes = "Delete grammar by id from user's personal list and return all grammar", response = List.class)
    public List<Word> deleteGrammar(@ApiParam(value = "Id value for grammar you need to delete", required = true)
                                    @RequestParam("grammarId") int grammarId,
                                    @ApiParam(value = "Id value for user to delete grammar", required = true)
                                    @RequestParam("userId") int userId) {
        logger.info("\"deleteGrammarFromPersonal?grammarId=" + grammarId + "&userId=" + userId + "\"");
        logger.info("Delete grammar from personal grammar list.");

        logger.info("Find user by id.");
        User user = userService.findById(userId);

        grammarService.deleteGrammarFromPersonalList(grammarId, user);

        logger.info("Return all grammar by user id.");
        return wordService.findAllByUserId(userId);
    }
}