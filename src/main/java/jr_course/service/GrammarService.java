package jr_course.service;

import jr_course.entity.Grammar;
import jr_course.entity.User;

import java.util.List;

public interface GrammarService {

    public List<Grammar> findAll();
    public List<Grammar> findAllByLevel(int level);
    public Grammar findById(int id);
    public List<Grammar> findAllByUserCollection_Id(int userId);
    public List<Grammar> findAllGrammarInPersonalList(int userId, String grammar);
    public List<Grammar> findByDifferentParameters(String param);
    public List<Grammar> findAllByParamAndLevel(String param, int level);

    public void addGrammarInPersonalList(User user, int grammarId);

    public void save(Grammar grammar);

    public void deleteById(int id);
    public void deleteGrammarFromPersonalList(int grammarId, User user);
}
