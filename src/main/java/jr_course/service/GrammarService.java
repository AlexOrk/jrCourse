package jr_course.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import jr_course.entity.Grammar;
import jr_course.entity.User;

import java.util.List;

public interface GrammarService {

    public List<Grammar> findAll();
    public List<Grammar> findAllByLevel(int level);
    public Grammar findById(int id);
    public List<Grammar> findAllByUserId(int userId);
    public List<Grammar> findByDifferentParameters(String param);

    public void addGrammarToPersonalList(User user, int grammarId);

    public void save(Grammar grammar);

    public void deleteById(int id);
    public void deleteGrammarFromPersonalList(int grammarId, User user);
}
