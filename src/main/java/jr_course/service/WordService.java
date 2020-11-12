package jr_course.service;


import jr_course.entity.User;
import jr_course.entity.Word;

import java.util.List;

public interface WordService {

    public List<Word> findAll();
    public List<Word> findAllByLevel(String level);
    public Word findById(int id);
    public List<Word> findAllByUserCollection_Id(int userId);
    public List<Word> findAllWordsInPersonalVocabulary(int userId, String word);
    public List<Word> findByDifferentParameters(String word);
    public List<Word> findAllByParamAndLevel(String param, String level);

    public void addWordInPersonalVocabulary(User user, int wordId);

    public void save(Word employee);

    public void deleteById(int id);
    public void deleteWordFromPersonalVocabulary(int wordId, User user);
}
