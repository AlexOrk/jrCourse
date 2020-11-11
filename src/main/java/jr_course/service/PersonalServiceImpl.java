package jr_course.service;

import jr_course.entity.Grammar;
import jr_course.entity.Word;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonalServiceImpl implements PersonalService {

    private Logger logger = LoggerFactory.getLogger(PersonalServiceImpl.class);

    @Override
    public List<Word> findAllWordsInPersonalWordsList(List<Word> words, String word) {
        logger.info("\"findAllWordsInPersonalWordsList\"");
        logger.info("Searching for words in the personal list.");

        List<Word> wordList = new ArrayList<>();
        for (Word w : words) {
            if (w.getJpKanji().contains(word) || w.getJpKana().contains(word) ||
                    w.getRuWord().contains(word)) {
                wordList.add(w);
            }
        }

        if (wordList.isEmpty()) {
            logger.info("wordList is empty.");
        }
        logger.info("Return wordList.");
        return wordList;
    }

    @Override
    public List<Grammar> findAllGrammarInPersonalWordsList(List<Grammar> grList, String grammar) {
        logger.info("\"findAllGrammarInPersonalWordsList\" method was called.");
        logger.info("Searching for grammar in the personal list.");

        List<Grammar> grammarList = new ArrayList<>();
        for (Grammar g : grList) {
            if (g.getFormula().contains(grammar) || g.getExample().contains(grammar) ||
                    g.getDescription().contains(grammar)) {
                grammarList.add(g);
            }
        }

        if (grammarList.isEmpty()) {
            logger.info("grammarList is empty.");
        }
        logger.info("Return grammarList.");
        return grammarList;
    }
}
