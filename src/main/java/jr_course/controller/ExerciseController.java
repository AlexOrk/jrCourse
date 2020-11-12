package jr_course.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jr_course.entity.Exercise;
import jr_course.entity.Grammar;
import jr_course.service.ExerciseService;
import jr_course.service.GrammarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

@RestController
@RequestMapping("/exercises")
public class ExerciseController {

    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    private ExerciseService exerciseService;
    private GrammarService grammarService;

    @Autowired
    public ExerciseController(ExerciseService exerciseService, GrammarService grammarService) {
        this.exerciseService = exerciseService;
        this.grammarService = grammarService;
    }

    @GetMapping("/")
    public List<Exercise> showExercises(@RequestParam("grammarId") int grammarId) {
        logger.info("\"/exercises/?grammarId=" + grammarId + "\"");

        return exerciseService.findAllByGrammar_Id(grammarId);
    }

    @PostMapping("/save")
    public Exercise saveExercise(@RequestBody String body, @RequestParam("grammarId") int grammarId) {
        logger.info("\"/exercises/saveExercise\"");

        ObjectMapper mapper = new ObjectMapper();
        StringReader reader = new StringReader(body);

        Exercise exercise = null;
        try {
            exercise = mapper.readValue(reader, Exercise.class);
            logger.info("Exercise was read.");
        } catch (IOException e) {
            logger.debug(e.getMessage());
            e.printStackTrace();
        }

        Grammar grammar = grammarService.findById(grammarId);
        exercise.setGrammar(grammar);
        exerciseService.save(exercise);
        logger.info("Exercise was saved!");
        return exercise;
    }

    @DeleteMapping("/delete")
    public List<Exercise> deleteExercise(@RequestParam("exerciseId") int exerciseId, @RequestParam("grammarId") int grammarId) {
        logger.info("\"/exercises/deleteExercise?exerciseId=" + exerciseId + "&grammarId=" + grammarId + "\"");

        exerciseService.deleteById(exerciseId);

        logger.info("Exercise was deleted!");
        logger.info("Return all exercises.");
        return exerciseService.findAllByGrammar_Id(grammarId);
    }
}
/*
Показать заметки +
Создать заметку +
Изменить заметку +
Удалить заметку +
*/