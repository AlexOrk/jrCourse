package jr_course.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import jr_course.entity.User;
import jr_course.entity.Word;
import jr_course.service.UserService;
import jr_course.service.WordService;
import jr_course.service.mq.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/words", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
public class WordController {

    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    private WordService wordService;
    private UserService userService;
    private Producer producer;

    @Autowired
    public WordController(WordService wordService, UserService userService, Producer producer) {
        this.wordService = wordService;
        this.userService = userService;
        this.producer = producer;
    }

    @GetMapping()
    @ApiOperation(value = "Find all words", notes = "Find and return all words", response = List.class)
    public List<Word> findWords() {
        logger.info("\"/words\"");

        List<Word> wordList = wordService.findAll();
        producer.sendMessage(wordList, "Word");
        return wordList;
    }

    @GetMapping("/lvl/{lvl}")
    @ApiOperation(value = "Find all words by level",
            notes = "Find and return all words by level", response = List.class)
    public List<Word> findWordsByLevel(@PathVariable String lvl) {
        logger.info("\"/words/lvl/" + lvl + "\"");

        return wordService.findAllByLevel(lvl);
    }

    @PostMapping(value = "/save", consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Save word", notes = "Save word to the word list", response = Word.class)
    public Word saveWord(@Valid @RequestBody Word word) {
        logger.info("\"/words/save\"");

        wordService.save(word);
        return word;
    }

    @DeleteMapping("/delete")
    @ApiOperation(value = "Delete word", notes = "Delete word by id and return all words", response = List.class)
    public List<Word> deleteWord(@ApiParam(value = "Id value for word you need to delete", required = true)
                                 @RequestParam("wordId") int wordId) {
        logger.info("\"/words/delete?wordId=" + wordId + "\"");

        wordService.deleteById(wordId);

        logger.info("Word was deleted!");
        logger.info("Return all words.");
        return wordService.findAll();
    }

    @GetMapping("/search")
    @ApiOperation(value = "Search word by param",
            notes = "If param exists, find and return words by param, otherwise return all", response = List.class)
    public List<Word> searchWord(@ApiParam(value = "Param value for word you need to find", required = true)
                                 @RequestParam(value = "param", required = false) String param,
                                 @ApiParam(value = "Id value for user whose word you need to find", required = true)
                                 @RequestParam("userId") int userId) {
        logger.info("\"/words/search?param=" + param + "&userId=" + userId + "\"");

        if (param == null || param.trim().isEmpty()) {
            logger.info("Return all words.");
            return wordService.findAll();
        }
        logger.info("Return words with an input parameter.");
        return wordService.findByDifferentParameters(param);
    }

    @PostMapping(value = "/addWordToPersonal",
                consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Add word to personal vocabulary",
            notes = "Add word by word id to user's personal vocabulary and return all words", response = List.class)
    public List<Word> addWordToPersonal(@ApiParam(value = "Id value for word you need to add", required = true)
                                        @RequestParam("wordId") int wordId,
                                        @ApiParam(value = "Level value to return word", required = false)
                                        @RequestParam(value = "lvl", required = false) String lvl,
                                        @ApiParam(value = "Id value for user to add word", required = true)
                                        @RequestParam("userId") int userId) {
        logger.info("\"/words/addWordToPersonal?wordId=" + wordId + "&lvl=" + lvl + "&userId=" + userId + "\"");
        logger.info("Add a word to personal vocabulary.");

        User user = userService.findById(userId);
        wordService.addWordToPersonalVocabulary(user, wordId);

        if (lvl != null) {
            logger.info("Word was on the lvl page, redirect to the lvl page.");
            return wordService.findAllByLevel(lvl);
        }
        logger.info("Return all words.");
        return wordService.findAll();
    }

    @DeleteMapping("/deleteWordFromPersonal")
    @ApiOperation(value = "Delete word from personal vocabulary",
            notes = "Delete word by id from user's personal vocabulary and return all words", response = List.class)
    public List<Word> deleteWordFromPersonal(@ApiParam(value = "Id value for word you need to delete", required = true)
                                             @RequestParam("wordId") int wordId,
                                             @ApiParam(value = "Level value to return word", required = false)
                                             @RequestParam(value = "lvl", required = false) String lvl,
                                             @ApiParam(value = "Id value for user to delete the word", required = true)
                                             @RequestParam("userId") int userId) {
        logger.info("\"deleteWordFromPersonal?wordId=" + wordId + "&lvl=" + lvl + "&userId=" + userId + "\"");
        logger.info("Delete word from personal vocabulary.");

        User user = userService.findById(userId);

        wordService.deleteWordFromPersonalVocabulary(wordId, user);
        logger.info("Word was deleted from personal vocabulary!");

        if (lvl != null) {
            logger.info("Word was on the lvl page, redirect to the lvl page.");
            return wordService.findAllByLevel(lvl);
        }
        logger.info("Return all words.");
        return wordService.findAll();
    }
}