package jr_course.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import jr_course.entity.Exercise;
import jr_course.entity.Note;

import java.util.List;

public interface ExerciseService {

    public List<Exercise> findAllByGrammarId(int grammarId);

    public void save(Exercise exercise);

    public void deleteById(int id);
}
