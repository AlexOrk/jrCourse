package jr_course.service;


import jr_course.entity.Word;

import java.util.List;

public interface WordService {

    public List<Word> findAll();
    public List<Word> findByLevelContains(String level);
    public Word findById(int id);

    public void save(Word employee);

    public void deleteById(int id);
}
