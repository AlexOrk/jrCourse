package jr_course.service;

import jr_course.dao.ExerciseRepository;
import jr_course.dao.GrammarRepository;
import jr_course.entity.Exercise;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import jr_course.exception.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExerciseServiceTest {

    @InjectMocks
    private ExerciseServiceImpl exerciseService;

    @Mock
    private ExerciseRepository exerciseRepository;
    @Mock
    private GrammarRepository grammarRepository;

    @Test
    public void findAllByUserId_test() {
        Exercise exercise1 = new Exercise();
        Exercise exercise2 = new Exercise();

        when(exerciseRepository.findAllByGrammar_Id(1)).thenReturn(Arrays.asList(exercise1, exercise2));
        when(grammarRepository.existsById(1)).thenReturn(true);
        List<Exercise> exercises = exerciseService.findAllByGrammarId(1);

        assertTrue(exercises.contains(exercise1) && exercises.contains(exercise2));

        when(grammarRepository.existsById(1)).thenReturn(false);

        assertEquals(DataNotFoundException.class, DataNotFoundException.class);

        when(exerciseRepository.findAllByGrammar_Id(1)).thenReturn(new ArrayList<>());
        when(grammarRepository.existsById(1)).thenReturn(true);
        exercises = exerciseService.findAllByGrammarId(1);

        assertTrue(exercises.isEmpty());
    }
}
