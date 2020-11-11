package jr_course.controller;

import jr_course.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/personal")
public class PersonalController {
    // Controller for personal users section

    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    private UserService userService;
    private WordService wordService;
    private GrammarService grammarService;
    private NoteService noteService;
    private PersonalService personalService;

    @Autowired
    public PersonalController(UserService userService, WordService wordService, GrammarService grammarService,
                              NoteService noteService, PersonalService personalService) {
        this.userService = userService;
        this.wordService = wordService;
        this.grammarService = grammarService;
        this.noteService = noteService;
        this.personalService = personalService;
    }

    // findAllWordsInPersonalWordsList(List<Word> words, String word)
    // findAllGrammarInPersonalWordsList(List<Grammar> grList, String grammar)
    // deleteWordFromPersonal
    // deleteGrammarFromPersonal
    // search/words
    // search/grammar
}
