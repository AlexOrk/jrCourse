package jr_course.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import jr_course.entity.Exercise;
import jr_course.entity.Grammar;
import jr_course.service.ExerciseService;
import jr_course.service.GrammarService;
import jr_course.service.mq.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/exercises", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
public class ExerciseController {

    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    private ExerciseService exerciseService;
    private GrammarService grammarService;
    private Producer producer;

    @Autowired
    public ExerciseController(ExerciseService exerciseService, GrammarService grammarService, Producer producer) {
        this.exerciseService = exerciseService;
        this.grammarService = grammarService;
        this.producer = producer;
    }

    @GetMapping()
    @ApiOperation(value = "Find all exercises", notes = "Find all exercises by grammar id", response = List.class)
    public List<Exercise> findExercises(@ApiParam(value = "Id value for grammar you need to retrieve", required = true)
                                        @RequestParam("grammarId") int grammarId) {
        logger.info("\"/exercises?grammarId=" + grammarId + "\"");

        List<Exercise> exerciseList = exerciseService.findAllByGrammarId(grammarId);
        producer.sendMessage(exerciseList, "Exercise");
        return exerciseList;
    }

    @PostMapping(value = "/save", consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Save exercise", notes = "Save exercise for grammar by grammar id", response = Exercise.class)
    public Exercise saveExercise(@Valid @RequestBody Exercise exercise,
                                 @ApiParam(value = "Id value for grammar you need to save", required = true)
                                 @RequestParam("grammarId") int grammarId) {
        logger.info("\"/exercises/saveExercise?grammarId=" + grammarId + "\"");

        Grammar grammar = grammarService.findById(grammarId);
        exercise.setGrammar(grammar);
        exerciseService.save(exercise);
        logger.info("Exercise was saved!");
        return exercise;
    }

    @DeleteMapping("/delete")
    @ApiOperation(value = "Delete exercise", notes = "Delete exercise by id, return all exercises", response = List.class)
    public List<Exercise> deleteExercise(@ApiParam(value = "Id value for exercise you need to delete", required = true)
                                         @RequestParam("exerciseId") int exerciseId,
                                         @ApiParam(value = "Id value for grammar whose exercise you need to delete",
                                                 required = true)
                                         @RequestParam("grammarId") int grammarId) {
        logger.info("\"/exercises/deleteExercise?exerciseId=" + exerciseId + "&grammarId=" + grammarId + "\"");

        exerciseService.deleteById(exerciseId);


        logger.info("Return all exercises.");
        return exerciseService.findAllByGrammarId(grammarId);
    }
}