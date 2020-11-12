package jr_course.service;

import jr_course.entity.Exercise;
import jr_course.entity.Note;

import java.util.List;

public interface ExerciseService {

    public List<Exercise> findAllByGrammar_Id(int id);

    public void save(Exercise exercise);

    public void deleteById(int id);
}
