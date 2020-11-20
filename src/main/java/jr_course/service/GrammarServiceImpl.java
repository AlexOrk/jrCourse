package jr_course.service;

import jr_course.dao.GrammarRepository;
import jr_course.entity.Grammar;
import jr_course.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jr_course.exception.*;

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
        logger.info("Find all grammar.");

        return grammarRepository.findAll();
    }

    @Override
    public List<Grammar> findAllByLevel(int level) {
        logger.info("\"findAllByLevelContains(level)\"");
        logger.info("Find all grammar depending on the \"" + level + "\" level.");
        if (!(level >= 1 && level <= 5))
            throw new IncorrectDataInputException("Incorrect level data input - " + level);

        return grammarRepository.findAllByLevel(level);
    }

    @Override
    public Grammar findById(int id) {
        logger.info("\"findById(id)\"");
        logger.info("Find grammar by id " + id + ".");
        Optional<Grammar> result = grammarRepository.findById(id);

        Grammar grammar = null;
        if (result.isPresent()) grammar = result.get();
        else {
            logger.warn("Grammar was not found.");
            throw new DataNotFoundException("Grammar with id " + id + " was not found.");
        }
        logger.info("Return grammar.");
        return grammar;
    }

    @Override
    public void save(Grammar grammar) {
        logger.info("\"save(grammar)\"");
        logger.info("Save a grammar in DB.");

        grammarRepository.save(grammar);
        logger.info("Grammar was saved!");
    }

    @Override
    public void deleteById(int id) {
        logger.info("\"deleteById(id)\"");
        logger.info("Delete a grammar by id " + id + ".");
        if (grammarRepository.existsById(id)) grammarRepository.deleteById(id);
        else throw new DataNotFoundException("Grammar with id \" + id + \" was not found.");
    }

    @Override
    public List<Grammar> findAllByUserId(int userId) {
        logger.info("\"findAllByUserCollection_Id(userId)\"");
        logger.info("Find all grammar by user id " + userId + ".");
        return grammarRepository.findAllByUserCollection_Id(userId);
    }

    @Override
    public void addGrammarToPersonalList(User user, int grammarId) {
        logger.info("\"addGrammarToPersonalList(user, grammarId)\"");
        logger.info("Add grammar to personal grammar list.");

        Grammar grammar = findById(grammarId);

        if (grammar.getUserCollection().contains(user)) {
            logger.info("Grammar was already added!");
        } else {
            logger.info("Add grammar.");
            grammar.addUser(user);

            logger.info("Update word.");
            grammarRepository.save(grammar);
            logger.info("Grammar was added to personal grammar list.");
        }
    }

    @Override
    public void deleteGrammarFromPersonalList(int grammarId, User user) {
        logger.info("\"deleteGrammarFromPersonalList(grammarId, user)\"");
        logger.info("Delete grammar by id " + grammarId + "from personal grammar list.");

        logger.info("Find grammar by id and delete user.");
        Grammar grammar = findById(grammarId);

        if (grammar.getUserCollection().contains(user)) {
            grammar.deleteUser(user);

            logger.info("Update grammar.");
            grammarRepository.save(grammar);
            logger.info("Grammar was deleted from personal grammar list!");
        } else logger.info("Grammar with id + " + grammarId + " was not found in the personal list.");
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
}
