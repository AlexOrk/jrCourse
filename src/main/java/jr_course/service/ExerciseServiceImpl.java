package jr_course.service;

import jr_course.dao.ExerciseRepository;
import jr_course.dao.GrammarRepository;
import jr_course.entity.Exercise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jr_course.exception.*;

import java.util.List;

@Service
public class ExerciseServiceImpl implements ExerciseService {
    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    private ExerciseRepository exerciseRepository;
    private GrammarRepository grammarRepository;

    @Autowired
    public ExerciseServiceImpl(ExerciseRepository exerciseRepository, GrammarRepository grammarRepository) {
        this.exerciseRepository = exerciseRepository;
        this.grammarRepository = grammarRepository;
    }

    @Override
    public List<Exercise> findAllByGrammarId(int grammarId) {
        logger.info("\"findAllByGrammar_Id(grammarId)\"");
        logger.info("Find all words by grammar id " + grammarId + ".");
        if (!grammarRepository.existsById(grammarId))
            throw new DataNotFoundException("Grammar with id " + grammarId + " was not found.");
        return exerciseRepository.findAllByGrammar_Id(grammarId);
    }

    @Override
    public void save(Exercise exercise) {
        logger.info("\"save(exercise)\"");
        logger.info("Save exercise " + exercise + ".");
        exerciseRepository.save(exercise);
    }

    @Override
    public void deleteById(int id) {
        logger.info("\"deleteById(id)\"");
        logger.info("Delete an exercise by id " + id + ".");
        if (exerciseRepository.existsById(id)) {
            exerciseRepository.deleteById(id);
            logger.info("Exercise was deleted!");
        } else throw new DataNotFoundException("Exercise with id " + id + " was not found.");
    }
}
