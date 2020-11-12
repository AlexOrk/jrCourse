package jr_course.service;

import jr_course.dao.GrammarRepository;
import jr_course.entity.Grammar;
import jr_course.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GrammarServiceImpl implements GrammarService {

    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    private GrammarRepository grammarRepository;

    @Autowired
    public GrammarServiceImpl(GrammarRepository grammarRepository) {
        this.grammarRepository = grammarRepository;
    }

    @Override
    public List<Grammar> findAll() {
        logger.info("\"findAll()\"");
        logger.info("Find all grammar in DB.");
        return grammarRepository.findAll();
    }

    @Override
    public List<Grammar> findAllByLevel(int level) {
        logger.info("\"findAllByLevelContains(level)\"");
        logger.info("Find all words in DB depending on the \"" + level + "\" level.");
        return grammarRepository.findAllByLevel(level);
    }

    @Override
    public Grammar findById(int id) {
        logger.info("\"findById(id)\"");
        logger.info("Find grammar by id " + id + ".");
        Optional<Grammar> result = grammarRepository.findById(id);

        Grammar grammar = null;

        if (result.isPresent()) {
            grammar = result.get();
        } else {
            logger.warn("Grammar not found.");
            throw new RuntimeException("Грамматика с id - " + id + " не была найдена.");
        }

        logger.info("Return grammar.");
        return grammar;
    }

    @Override
    public void save(Grammar grammar) {
        logger.info("\"save(grammar)\"");
        logger.info("Save a grammar in DB.");
        grammarRepository.save(grammar);
    }

    @Override
    public void deleteById(int id) {
        logger.info("\"deleteById(id)\"");
        logger.info("Delete a grammar in DB by id " + id + ".");
        grammarRepository.deleteById(id);
    }

    @Override
    public List<Grammar> findAllByUserCollection_Id(int userId) {
        logger.info("\"findAllByUserCollection_Id(userId)\"");
        logger.info("Find all grammar by user id " + userId + ".");
        return grammarRepository.findAllByUserCollection_Id(userId);
    }

    @Override
    public List<Grammar> findAllGrammarInPersonalList(int userId, String grammar) {
        logger.info("\"findAllGrammarInPersonalList\" method was called.");
        logger.info("Searching for grammar in the personal list.");

        List<Grammar> personalGrammarList = grammarRepository.findAllByUserCollection_Id(userId);
        List<Grammar> grammarList = new ArrayList<>();

        for (Grammar g : personalGrammarList) {
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

    @Override
    public void addGrammarInPersonalList(User user, int grammarId) {
        logger.info("\"addGrammarInPersonalGrammarList(user, grammarId)\"");
        logger.info("Add grammar in personal grammar list.");

        Grammar grammar = findById(grammarId);

        logger.info("Add grammar.");
        grammar.addUser(user);

        logger.info("Update word.");
        grammarRepository.save(grammar);
        logger.info("Grammar was added in personal grammar list.");
    }

    @Override
    public void deleteGrammarFromPersonalList(int grammarId, User user) {
        logger.info("\"deleteGrammarFromPersonalList(grammarId, user)\"");
        logger.info("Delete grammar by id " + grammarId + "from personal grammar list.");

        logger.info("Find grammar by id and delete user.");
        Grammar grammar = findById(grammarId);
        grammar.deleteUser(user);

        logger.info("Update grammar.");
        grammarRepository.save(grammar);
        logger.info("Grammar was deleted from personal grammar list!");
    }

    @Override
    public List<Grammar> findByDifferentParameters(String param) {
        logger.info("\"findByDifferentParameters(param)\"");
        logger.info("Find grammar by parameter " + param + ".");

        param = param.trim();
        List<Grammar> grammarList = grammarRepository
                .findByFormulaContainsOrExampleContainsAllIgnoreCase(param, param);

        if (grammarList.isEmpty()) logger.info("Grammar list is empty.");

        logger.info("Return grammar list.");
        return grammarList;
    }

    @Override
    public List<Grammar> findAllByParamAndLevel(String param, int level) {
        logger.info("\"findAllByParamAndLevel(param, level)\"");
        logger.info("Find all grammar by parameter and level.");

        param = param.trim();
        logger.info("Find grammar by level.");
        List<Grammar> grammarList = grammarRepository.findAllByLevel(level);

        if (grammarList.isEmpty()) {
            logger.info("Grammar list is empty.");
            return grammarList;
        }

        logger.info("Find grammar by parameter.");
        List<Grammar> foundedListByGrammarAndLevel = new ArrayList<>();
        for (Grammar w : grammarList) {
            if (w.getFormula().contains(param) || w.getExample().contains(param)) {
                foundedListByGrammarAndLevel.add(w);
            }
        }

        if (foundedListByGrammarAndLevel.isEmpty()) logger.info("Founded grammar list is empty.");

        logger.info("Return grammar list.");
        return foundedListByGrammarAndLevel;
    }
}
