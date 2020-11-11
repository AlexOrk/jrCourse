package jr_course.service;

import jr_course.dao.GrammarRepository;
import jr_course.entity.Grammar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<Grammar> findByLevel(int level) {
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

}
