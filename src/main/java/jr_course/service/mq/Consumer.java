package jr_course.service.mq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jr_course.dao.ExerciseRepository;
import jr_course.dao.GrammarRepository;
import jr_course.dao.NoteRepository;
import jr_course.dao.WordRepository;
import jr_course.entity.*;
import jr_course.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Consumer {

    private ObjectMapper objectMapper = new ObjectMapper();
    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    private WordRepository wordRepository;
    private NoteRepository noteRepository;
    private GrammarRepository grammarRepository;
    private ExerciseRepository exerciseRepository;

    @Autowired
    public Consumer(WordRepository wordRepository, NoteRepository noteRepository,
                    GrammarRepository grammarRepository, ExerciseRepository exerciseRepository) {
        this.wordRepository = wordRepository;
        this.noteRepository = noteRepository;
        this.grammarRepository = grammarRepository;
        this.exerciseRepository = exerciseRepository;
    }

    // Get

    @RabbitListener(queues = "q.get.exercise.work")
    public void listenExercise(String message) throws JsonProcessingException, AmqpRejectAndDontRequeueException {
        List<Exercise> exercises = objectMapper.readValue(message, List.class);
        if (exercises.isEmpty())
            throw new AmqpRejectAndDontRequeueException("Exercise list is empty!");
        logger.info("On exercise: " + exercises.toString());
    }

    @RabbitListener(queues = "q.get.note.work")
    public void listenNote(String message) throws JsonProcessingException, AmqpRejectAndDontRequeueException {
        List<Note> notes = objectMapper.readValue(message, List.class);
        if (notes.isEmpty())
            throw new AmqpRejectAndDontRequeueException("Note list is empty!");
        logger.info("On note : " + notes.toString());
    }

    @RabbitListener(queues = "q.get.word.work")
    public void listenWord(String message) throws JsonProcessingException, AmqpRejectAndDontRequeueException {
        List<Word> words = objectMapper.readValue(message, List.class);
        if (words.isEmpty())
            throw new AmqpRejectAndDontRequeueException("Word list is empty!");
        logger.info("On word : " + words.toString());
    }

    @RabbitListener(queues = "q.get.grammar.work")
    public void listenGrammar(String message) throws JsonProcessingException, AmqpRejectAndDontRequeueException {
        List<Grammar> grammar = objectMapper.readValue(message, List.class);
        if (grammar.isEmpty())
            throw new AmqpRejectAndDontRequeueException("Grammar list is empty!");
        logger.info("On grammar: " + grammar.toString());
    }

    @RabbitListener(queues = "q.get.user.work")
    public void listenUser(String message) throws JsonProcessingException, AmqpRejectAndDontRequeueException {
        List<User> users = objectMapper.readValue(message, List.class);
        if (users.isEmpty())
            throw new AmqpRejectAndDontRequeueException("User list is empty!");
        logger.info("On user: " + users.toString());
    }

    // Post

    @RabbitListener(queues = "q.post.word.work")
    public void listenPostWord(String message) throws JsonProcessingException, AmqpRejectAndDontRequeueException {
        Word word = objectMapper.readValue(message, Word.class);

        if (!isCorrectWord(word)) throw new IncorrectDataInputException("Incorrect word data input.");
        wordRepository.save(word);
        logger.info("Word was saved!");
    }

    @RabbitListener(queues = "q.post.note.work")
    public void listenPostNote(String message) throws JsonProcessingException, AmqpRejectAndDontRequeueException {
        Note note = objectMapper.readValue(message, Note.class);

        noteRepository.save(note);
        logger.info("Note was saved!");
    }

    @RabbitListener(queues = "q.post.grammar.work")
    public void listenPostGrammar(String message) throws JsonProcessingException, AmqpRejectAndDontRequeueException {
        Grammar grammar = objectMapper.readValue(message, Grammar.class);

        grammarRepository.save(grammar);
        logger.info("Grammar was saved!");
    }

    @RabbitListener(queues = "q.post.exercise.work")
    public void listenPostExercise(String message) throws JsonProcessingException, AmqpRejectAndDontRequeueException {
        Exercise exercise = objectMapper.readValue(message, Exercise.class);

        exerciseRepository.save(exercise);
        logger.info("Exercise was saved!");
    }

    // Util
    // for exception demo
    public boolean isCorrectWord(Word word) {
        if (word.getLevel() == null
                || word.getLevel().isEmpty()
                || !(word.getLevel().matches("^easy$|^medium$|^hard$") && word.getLevel().length() <= 10))
            return false;

        if (word.getJpKanji() != null && !word.getJpKanji().isEmpty()) {
            if (!(word.getJpKanji().matches("^[\\p{sc=Han}\\p{sc=Hiragana}\\p{sc=Katakana}]+$")
                    && word.getJpKanji().length() <= 20))
                return false;
        }
        if (word.getJpKana() == null
                || word.getJpKana().isEmpty()
                || !(word.getJpKana().matches("^[\\p{sc=Hiragana}\\p{sc=Katakana}]+$") && word.getJpKana().length() <= 20))
            return false;

        if (word.getRuWord() == null
                || word.getRuWord().isEmpty()
                || !(word.getRuWord().matches("^([а-яА-Я0-9()/.,\\-!?]+(\\s)?)+$") && word.getRuWord().length() <= 150))
            return false;

        return word.getDescription() != null;
    }
}
