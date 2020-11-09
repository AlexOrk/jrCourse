package jr_course.controller;

//import alex.ork.springboot.course.entity.UsersData;
//import alex.ork.springboot.course.entity.Word;
//import alex.ork.springboot.course.service.UsersDataService;
//import alex.ork.springboot.course.service.WordService;
import jr_course.dao.WordRepository;
import jr_course.service.WordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/words")
public class WordController {
    // Controller for words section

    private Logger logger = LoggerFactory.getLogger(WordController.class);

//    private WordRepository wordRepository;
    private WordService wordService;
//    private UsersDataService usersDataService;

    @Autowired
    public WordController(WordService wordService) {
        this.wordService = wordService;
    }

    @GetMapping("/")
    public String words() {
        logger.info("\"/words/\"");

        return wordService.findAll().toString();
    }
}
