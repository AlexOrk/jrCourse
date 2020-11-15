package jr_course.controller;

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
    public List<Word> showWords(@RequestParam("userId") int userId) {
        logger.info("\"/personal/words?userId=" + userId + "\"");

        return wordService.findAllByUserCollectionId(userId);
    }

    @GetMapping("/grammar")
    public List<Grammar> showGrammar(@RequestParam("userId") int userId) {
        logger.info("\"/personal/grammar?userId=" + userId + "\"");

        return grammarService.findAllByUserCollectionId(userId);
    }

    @DeleteMapping("/delete/word")
    public List<Word> deleteWord(@RequestParam("wordId") int wordId,
                                 @RequestParam("userId") int userId) {
        logger.info("\"deleteWordFromPersonal?wordId=" + wordId + "&userId=" + userId + "\"");
        logger.info("Delete word from personal vocabulary.");

        logger.info("Find user by id.");
        User user = userService.findById(userId);

        wordService.deleteWordFromPersonalVocabulary(wordId, user);

        logger.info("Return all words by user id.");
        return wordService.findAllByUserCollectionId(userId);
    }

    @DeleteMapping("/delete/grammar")
    public List<Word> deleteGrammar(@RequestParam("grammarId") int grammarId,
                                    @RequestParam("userId") int userId) {
        logger.info("\"deleteGrammarFromPersonal?grammarId=" + grammarId + "&userId=" + userId + "\"");
        logger.info("Delete grammar from personal grammar list.");

        logger.info("Find user by id.");
        User user = userService.findById(userId);

        grammarService.deleteGrammarFromPersonalList(grammarId, user);

        logger.info("Return all grammar by user id.");
        return wordService.findAllByUserCollectionId(userId);
    }
}