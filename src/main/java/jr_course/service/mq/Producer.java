package jr_course.service.mq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jr_course.dao.ExerciseRepository;
import jr_course.dao.GrammarRepository;
import jr_course.dao.NoteRepository;
import jr_course.dao.WordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope(value = "prototype")
public class Producer<T> {

    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    private RabbitTemplate rabbitTemplate;
    private ExerciseRepository exerciseRepository;
    private NoteRepository noteRepository;
    private GrammarRepository grammarRepository;
    private WordRepository wordRepository;

    @Autowired
    public Producer(RabbitTemplate rabbitTemplate, ExerciseRepository exerciseRepository,
                    NoteRepository noteRepository, GrammarRepository grammarRepository, WordRepository wordRepository) {
        this.rabbitTemplate = rabbitTemplate;
        this.exerciseRepository = exerciseRepository;
        this.noteRepository = noteRepository;
        this.grammarRepository = grammarRepository;
        this.wordRepository = wordRepository;
    }

    private ObjectMapper objectMapper = new ObjectMapper();

    public void sendMessage(List<?> message, String type) {
        String json = null;
        try {
            json = objectMapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            logger.warn(e.getMessage());
        }
        rabbitTemplate.convertAndSend("x.get.work", type, json);
    }

    public void sendMessage(T message) {

        String json = null;
        try {
            json = objectMapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            logger.warn(e.getMessage());
        }
        rabbitTemplate.convertAndSend("x.post.work", message.getClass().getSimpleName(), json);
    }
}
