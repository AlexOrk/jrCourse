package jr_course.service;


import jr_course.entity.User;
import jr_course.entity.Word;

import java.util.List;

public interface WordService {

    public List<Word> findAll();
    public List<Word> findAllByLevel(String level);
    public Word findById(int id);
    public List<Word> findAllByUserId(int userId);
    public List<Word> findByDifferentParameters(String word);

    public void addWordToPersonalVocabulary(User user, int wordId);

    public void save(Word word);

    public void deleteById(int id);
    public void deleteWordFromPersonalVocabulary(int wordId, User user);
}
