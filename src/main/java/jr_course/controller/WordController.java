package jr_course.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jr_course.entity.User;
import jr_course.entity.Word;
import jr_course.service.UserService;
import jr_course.service.WordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

@RestController
@RequestMapping(value = "/words", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
public class WordController {

    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    private WordService wordService;
    private UserService userService;

    @Autowired
    public WordController(WordService wordService, UserService userService) {
        this.wordService = wordService;
        this.userService = userService;
    }

    @GetMapping()
    public List<Word> showWords() {
        logger.info("\"/words\"");

        return wordService.findAll();
    }

    @GetMapping("/lvl/{lvl}")
    public List<Word> showWordsByLevel(@PathVariable String lvl) {
        logger.info("\"/words/lvl/" + lvl + "\"");

        return wordService.findAllByLevel(lvl);
    }

    @PostMapping(value = "/save", consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public Word saveWord(@RequestBody Word word) {
        logger.info("\"/words/saveWord\"");

        // no need yet
//        ObjectMapper mapper = new ObjectMapper();
//        StringReader reader = new StringReader(body);
//
//        Word word = null;
//        try {
//            word = mapper.readValue(reader, Word.class);
//            logger.info("Word was read.");
//        } catch (IOException e) {
//            logger.debug(e.getMessage());
//            e.printStackTrace();
//        }
        wordService.save(word);
        logger.info("Word was saved!");
        return word;
    }

    @DeleteMapping("/delete")
    public List<Word> deleteWord(@RequestParam("wordId") int wordId) {
        logger.info("\"/words/deleteWord?wordId=" + wordId + "\"");

        wordService.deleteById(wordId);

        logger.info("Word was deleted!");
        logger.info("Return all words.");
        return wordService.findAll();
    }

    @GetMapping("/search")
    public List<Word> searchWord(@RequestParam(value = "param", required = false) String param,
                                 @RequestParam("userId") int userId) {
        logger.info("\"/words/search?param=" + param + "&userId=" + userId + "\"");

        if (param == null || param.trim().isEmpty()) {
            logger.info("Return all words.");
            return wordService.findAll();
        }

        logger.info("Return words with an input parameter.");
        return wordService.findByDifferentParameters(param);
    }

    @PostMapping(value = "/addWordInPersonal",
                consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public List<Word> addWordInPersonal(@RequestParam("wordId") int wordId,
                                    @RequestParam(value = "lvl", required = false) String lvl,
                                    @RequestParam("userId") int userId) {
        logger.info("\"/words/addWordInPersonal?wordId=" + wordId + "&lvl=" + lvl + "&userId=" + userId + "\"");
        logger.info("Add a word in personal vocabulary.");

        User user = userService.findById(userId);

        wordService.addWordInPersonalVocabulary(user, wordId);

        if (lvl != null) {
            logger.info("Word was on the lvl page, redirect to the lvl page.");
            return wordService.findAllByLevel(lvl);
        }

        logger.info("Return all words.");
        return wordService.findAll();
    }

    @DeleteMapping("/deleteWordFromPersonal")
    public List<Word> deleteWordFromPersonal(@RequestParam("wordId") int wordId,
                                         @RequestParam(value = "lvl", required = false) String lvl,
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