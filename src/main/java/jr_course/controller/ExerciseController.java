package jr_course.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jr_course.entity.Exercise;
import jr_course.entity.Grammar;
import jr_course.service.ExerciseService;
import jr_course.service.GrammarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

@RestController
@RequestMapping(value = "/exercises", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
public class ExerciseController {

    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    private ExerciseService exerciseService;
    private GrammarService grammarService;

    @Autowired
    public ExerciseController(ExerciseService exerciseService, GrammarService grammarService) {
        this.exerciseService = exerciseService;
        this.grammarService = grammarService;
    }

    @GetMapping()
    public List<Exercise> showExercises(@RequestParam("grammarId") int grammarId) {
        logger.info("\"/exercises?grammarId=" + grammarId + "\"");

        return exerciseService.findAllByGrammar_Id(grammarId);
    }

    @PostMapping(value = "/save", consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public Exercise saveExercise(@RequestBody Exercise exercise, @RequestParam("grammarId") int grammarId) {
        logger.info("\"/exercises/saveExercise\"");

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