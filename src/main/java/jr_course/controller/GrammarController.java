package jr_course.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import jr_course.entity.Grammar;
import jr_course.entity.User;
import jr_course.service.GrammarService;
import jr_course.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

@RestController
@RequestMapping(value = "/grammar", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
public class GrammarController {

    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    private GrammarService grammarService;
    private UserService userService;

    @Autowired
    public GrammarController(GrammarService grammarService, UserService userService) {
        this.grammarService = grammarService;
        this.userService = userService;
    }

    @GetMapping()
    @ApiOperation(value = "Show all grammar", notes = "Find and return all grammar", response = List.class)
    public List<Grammar> showGrammar() {
        logger.info("\"/grammar\"");

        return grammarService.findAll();
    }

    @GetMapping("/lvl/{lvl}")
    @ApiOperation(value = "Show all grammar by level",
            notes = "Find and return all grammar by level", response = List.class)
    public List<Grammar> showGrammarByLevel(@PathVariable int lvl) {
        logger.info("\"/grammar/lvl/" + lvl + "\"");

        return grammarService.findAllByLevel(lvl);
    }

    @PostMapping(value = "/save", consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Save grammar", notes = "Save grammar to the grammar list", response = Grammar.class)
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
    @ApiOperation(value = "Delete grammar", notes = "Delete grammar by id and return all grammar", response = List.class)
    public List<Grammar> deleteGrammar(@ApiParam(value = "Id value for grammar you need to delete", required = true)
                                       @RequestParam("grammarId") int grammarId) {
        logger.info("\"/grammar/deleteGrammar?grammarId" + grammarId + "\"");

        grammarService.deleteById(grammarId);

        logger.info("Grammar was deleted!");
        logger.info("Return all grammar.");
        return grammarService.findAll();
    }

    @GetMapping("/search")
    @ApiOperation(value = "Search grammar by param",
            notes = "If param exists, find and return grammar by param, otherwise return all", response = List.class)
    public List<Grammar> searchGrammar(@ApiParam(value = "Param value for grammar you need to find", required = true)
                                       @RequestParam(value = "param", required = false) String param,
                                       @ApiParam(value = "Id value for user whose grammar you need to find", required = true)
                                       @RequestParam("userId") int userId) {
        logger.info("\"/grammar/search?param=" + param + "&userId=" + userId + "\"");

        if (param == null || param.trim().isEmpty()) {
            logger.info("Return all words.");
            return grammarService.findAll();
        }

        logger.info("Return words with an input parameter.");
        return grammarService.findByDifferentParameters(param);
    }

    @PostMapping(value = "/addGrammarToPersonal",
            consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Add grammar to personal list",
            notes = "Add grammar by grammar id to user's personal list and return all grammar", response = List.class)
    public List<Grammar> addGrammarToPersonal(@ApiParam(value = "Id value for grammar you need to add", required = true)
                                              @RequestParam("grammarId") int grammarId,
                                              @ApiParam(value = "Level value to return grammar", required = false)
                                              @RequestParam(value = "lvl", required = false) Integer lvl,
                                              @ApiParam(value = "Id value for user to add grammar", required = true)
                                              @RequestParam("userId") int userId) {
        logger.info("\"/grammar/addGrammarToPersonal?grammarId=" + grammarId + "&lvl=" + lvl + "&userId=" + userId + "\"");
        logger.info("Add grammar to personal grammar list.");

        User user = userService.findById(userId);

        grammarService.addGrammarToPersonalList(user, grammarId);

        if (lvl != null) {
            logger.info("Grammar was on the lvl page, redirect to the lvl page.");
            return grammarService.findAllByLevel(lvl);
        }

        logger.info("Return all grammar.");
        return grammarService.findAll();
    }

    @DeleteMapping("/deleteGrammarFromPersonal")
    @ApiOperation(value = "Delete grammar from personal list",
            notes = "Delete grammar by id from user's personal list and return all grammar", response = List.class)
    public List<Grammar> deleteGrammarFromPersonal(@ApiParam(value = "Id value for grammar you need to delete", required = true)
                                                   @RequestParam("grammarId") int grammarId,
                                                   @ApiParam(value = "Level value to return grammar", required = false)
                                                   @RequestParam(value = "lvl", required = false) Integer lvl,
                                                   @ApiParam(value = "Id value for user to delete grammar", required = true)
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