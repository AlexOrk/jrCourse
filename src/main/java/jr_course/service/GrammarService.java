package jr_course.service;

import jr_course.entity.Grammar;

import java.util.List;

public interface GrammarService {

    public List<Grammar> findAll();
    public List<Grammar> findByLevel(int level);
    public Grammar findById(int id);

    public void save(Grammar grammar);

    public void deleteById(int id);

//    public List<Grammar> searchBy(String name);

}
