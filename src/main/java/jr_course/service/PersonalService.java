package jr_course.service;


import jr_course.entity.Grammar;
import jr_course.entity.Word;

import java.util.List;

public interface PersonalService {
    public List<Word> findAllWordsInPersonalWordsList(List<Word> words, String word);

    public List<Grammar> findAllGrammarInPersonalWordsList(List<Grammar> grammarList, String gramar);
}
