package jr_course.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jr_course.entity.Grammar;
import jr_course.entity.User;
import jr_course.service.GrammarService;
import jr_course.service.UserService;
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
    private UserService userService;

    @Autowired
    public GrammarController(GrammarService grammarService, UserService userService) {
        this.grammarService = grammarService;
        this.userService = userService;
    }

    @GetMapping("/")
    public List<Grammar> showGrammar() {
        logger.info("\"/grammar/\"");

        return grammarService.findAll();
    }

    @GetMapping("/lvl/{lvl}")
    public List<Grammar> showGrammarByLevel(@PathVariable int lvl) {
        logger.info("\"/grammar/lvl/" + lvl + "\"");

        return grammarService.findAllByLevel(lvl);
    }

    @PostMapping("/save")
    public Grammar saveGrammar(@RequestBody Grammar grammar) {
        logger.info("\"/grammar/saveGrammar\"");

        // no need yet
//        ObjectMapper mapper = new ObjectMapper();
//        StringReader reader = new StringReader(body);
//
//        Grammar grammar = null;
//
//        try {
//            grammar = mapper.readValue(reader, Grammar.class);
//            logger.info("Grammar was read.");
//        } catch (IOException e) {
//            logger.debug(e.getMessage());
//            e.printStackTrace();
//        }
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

    @GetMapping("/search")
    public List<Grammar> searchGrammar(@RequestParam(value = "param", required = false) String param,
                                       @RequestParam("userId") int userId) {
        logger.info("\"/grammar/search?param=" + param + "&userId=" + userId + "\"");

        if (param == null || param.trim().isEmpty()) {
            logger.info("Return all words.");
            return grammarService.findAll();
        }

        logger.info("Return words with an input parameter.");
        return grammarService.findByDifferentParameters(param);
    }

    @PostMapping("/addGrammarInPersonal")
    public List<Grammar> addGrammarInPersonal(@RequestParam("grammarId") int grammarId,
                                        @RequestParam(value = "lvl", required = false) Integer lvl,
                                        @RequestParam("userId") int userId) {
        logger.info("\"/grammar/addGrammarInPersonal?grammarId=" + grammarId + "&lvl=" + lvl + "&userId=" + userId + "\"");
        logger.info("Add a grammar in personal grammar list.");

        User user = userService.findById(userId);

        grammarService.addGrammarInPersonalList(user, grammarId);

        if (lvl != null) {
            logger.info("Grammar was on the lvl page, redirect to the lvl page.");
            return grammarService.findAllByLevel(lvl);
        }

        logger.info("Return all grammar.");
        return grammarService.findAll();
    }

    @DeleteMapping("/deleteGrammarFromPersonal")
    public List<Grammar> deleteGrammarFromPersonal(@RequestParam("grammarId") int grammarId,
                                             @RequestParam(value = "lvl", required = false) Integer lvl,
                                             @RequestParam("userId") int userId) {
        logger.info("\"deleteGrammarFromPersonal?grammarId=" + grammarId + "&lvl=" + lvl + "&userId=" + userId + "\"");
        logger.info("Delete word from personal grammar list.");

        User user = userService.findById(userId);

        grammarService.deleteGrammarFromPersonalList(grammarId, user);

        logger.info("Grammar was deleted from personal grammar list!");

        if (lvl != null) {
            logger.info("Grammar was on the lvl page, redirect to the lvl page.");
            return grammarService.findAllByLevel(lvl);
        }

        logger.info("Return all grammar.");
        return grammarService.findAll();
    }
}

/*
Вывести всю грамматику +
Вывести грамматику определенного уровня +
Сохранить новую грамматику +
Изменить грамматику +
Удалить грамматику +
Найти грамматику по указанному параметру +
Добавить представленную в общем списке грамматику в персональный лист +
Удалить представленную в общем списке грамматику из персонального листа +
 */