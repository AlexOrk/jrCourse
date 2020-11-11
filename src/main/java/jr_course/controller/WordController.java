package jr_course.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jr_course.entity.Word;
import jr_course.service.WordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;


@RestController
@RequestMapping("/words")
public class WordController {

    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    private WordService wordService;

    @Autowired
    public WordController(WordService wordService) {
        this.wordService = wordService;
    }

    @GetMapping("/")
    public List<Word> showWords() {
        logger.info("\"/words/\"");

        return wordService.findAll();
    }

    // we don't need check for null because we'll get var from path
    @GetMapping("/lvl/{lvl}")
    public List<Word> showWordsByLevel(@PathVariable String lvl) {
        logger.info("\"/words/lvl/" + lvl + "\"");

        return wordService.findByLevelContains(lvl);
    }

    @GetMapping("/word")
    public Word getWordById(@RequestParam("wordId") int wordId) {
        logger.info("\"/words/getWord?wordId=" + wordId + "\"");

        return wordService.findById(wordId);
    }

    // save, also update
    @PostMapping("/save")
    public Word saveWord(@RequestBody String body) {
        logger.info("\"/words/saveWord\"");

        ObjectMapper mapper = new ObjectMapper();
        StringReader reader = new StringReader(body);

        Word word = null;
        try {
            word = mapper.readValue(reader, Word.class);
            logger.info("Word was read.");
        } catch (IOException e) {
            logger.debug(e.getMessage());
            e.printStackTrace();
        }
        wordService.save(word);
        logger.info("Word was saved!");
        return word;
    }

    // we don't need check for null because we'll get param by button
    @DeleteMapping("/delete")
    public List<Word> deleteWord(@RequestParam("wordId") int wordId) {
        logger.info("\"/words/deleteWord?wordId=" + wordId + "\"");

        wordService.deleteById(wordId);

        logger.info("Word was deleted!");
        logger.info("Return all words.");
        return wordService.findAll();
    }
}

// searchWord
/*
Вывести все слова +
Вывести слова определенного уровня +
Вывести слово по id +
Сохранить новое слово +
Изменить слово +
Удалить слово +
 */