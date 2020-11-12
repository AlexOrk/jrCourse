package jr_course.controller;

import jr_course.entity.Grammar;
import jr_course.entity.User;
import jr_course.entity.Word;
import jr_course.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/personal")
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

        return wordService.findAllByUserCollection_Id(userId);
    }

    @GetMapping("/grammar")
    public List<Grammar> showGrammar(@RequestParam("userId") int userId) {
        logger.info("\"/personal/grammar?userId=" + userId + "\"");

        return grammarService.findAllByUserCollection_Id(userId);
    }

    @GetMapping("/search/words")
    public List<Word> searchWords(@RequestParam(value = "word", required = false) String word,
                                  @RequestParam("userId") int userId) {
        logger.info("\"/personal/search/words?word=" + word + "&userId=" + userId + "\"");

        if (word == null || word.trim().isEmpty()) {
            logger.info("No word containing the required value or no value. Return all words by user id.");
            return wordService.findAllByUserCollection_Id(userId);
        }

        logger.info("Find all words containing the required value by user id.");
        return wordService.findAllWordsInPersonalVocabulary(userId, word);
    }

    @GetMapping("/search/grammar")
    public List<Grammar> searchGrammar(@RequestParam(value = "grammar", required = false) String grammar,
                                       @RequestParam("userId") int userId) {
        logger.info("\"/personal/search/grammar?grammar=" + grammar + "&userId=" + userId + "\"");

        if (grammar == null || grammar.trim().isEmpty()) {
            logger.info("No grammar containing the required value or no value. Return all grammar by user id.");
            return grammarService.findAllByUserCollection_Id(userId);
        }

        logger.info("Find all grammar containing the required value by user id.");
        return grammarService.findAllGrammarInPersonalList(userId, grammar);
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
        return wordService.findAllByUserCollection_Id(userId);
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
        return wordService.findAllByUserCollection_Id(userId);
    }
}


/*
Вывести все слова из персонального списка +
Вывести всю грамматику из персонального списка +
Найти слова из персонального списка по указанному параметру +
Найти грамматику из персонального списка по указанному параметру +
Удалить слово из персонального списка +
Удалить грамматику из персонального списка +
 */