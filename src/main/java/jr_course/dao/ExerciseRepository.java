package jr_course.dao;

import jr_course.entity.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExerciseRepository extends JpaRepository<Exercise, Integer> {

    public List<Exercise> findAllByGrammar_Id(int id);
}
