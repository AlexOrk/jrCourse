package jr_course.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jr_course.entity.Grammar;
import jr_course.service.GrammarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

@RestController
@RequestMapping("/grammar")
public class GrammarController {

    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    private GrammarService grammarService;

    @Autowired
    public GrammarController(GrammarService grammarService) {
        this.grammarService = grammarService;
    }

    @GetMapping("/")
    public List<Grammar> showGrammar() {
        logger.info("\"/grammar/\"");

        return grammarService.findAll();
    }

    @GetMapping("/lvl/{lvl}")
    public List<Grammar> showGrammarByLevel(@PathVariable int lvl) {
        logger.info("\"/grammar/lvl/" + lvl + "\"");

        return grammarService.findByLevel(lvl);
    }

//    @GetMapping("/grammar")
//    public Grammar getGrammarById(@RequestParam("grammarId") int grammarId) {
//        logger.info("\"/words/getWord?grammarId=" + grammarId + "\"");
//
//        return grammarService.findById(grammarId);
//    }

    @PostMapping("/save")
    public Grammar saveGrammar(@RequestBody String body) {
        logger.info("\"/grammar/saveGrammar\"");

        ObjectMapper mapper = new ObjectMapper();
        StringReader reader = new StringReader(body);

        Grammar grammar = null;

        try {
            grammar = mapper.readValue(reader, Grammar.class);
            logger.info("Grammar was read.");
        } catch (IOException e) {
            logger.debug(e.getMessage());
            e.printStackTrace();
        }
        grammarService.save(grammar);
        logger.info("Grammar was saved!");
        return grammar;
    }

    @DeleteMapping("/delete")
    public List<Grammar> deleteGrammar(@RequestParam("grammarId") int grammarId) {
        logger.info("\"/grammar/deleteGrammar?grammarId" + grammarId + "\"");

        grammarService.deleteById(grammarId);

        logger.info("Grammar was deleted!");
        logger.info("Return all grammar.");
        return grammarService.findAll();
    }
}
// SearchGrammar
/*
Вывести всю грамматику +
Вывести грамматику определенного уровня +
Вывести грамматику по id +
Сохранить новую грамматику +
Изменить грамматику +
Удалить грамматику +
 */