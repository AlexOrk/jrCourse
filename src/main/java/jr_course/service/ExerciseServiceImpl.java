package jr_course.service;

import jr_course.dao.ExerciseRepository;
import jr_course.dao.NoteRepository;
import jr_course.entity.Exercise;
import jr_course.entity.Note;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExerciseServiceImpl implements ExerciseService {
    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    private ExerciseRepository exerciseRepository;

    @Autowired
    public ExerciseServiceImpl(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    @Override
    public List<Exercise> findAllByGrammar_Id(int id) {
        logger.info("\"findAllByGrammar_Id(id)\"");
        logger.info("Find all words by grammar id " + id + ".");
        return exerciseRepository.findAllByGrammar_Id(id);
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
        exerciseRepository.deleteById(id);
    }
}
