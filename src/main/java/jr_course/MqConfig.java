package jr_course;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class MqConfig {
    Map<String, Object> getArgs = new HashMap<>();
    Map<String, Object> postArgs = new HashMap<>();

    {
        getArgs.put("x-dead-letter-exchange", "x.get.dead");
        postArgs.put("x-dead-letter-exchange", "x.post.dead");
    }

    @Bean
    public Declarables createMqSchema() {
        return new Declarables(
                new DirectExchange("x.get.work", true, false, null),
                new DirectExchange("x.get.dead", true, false, null),
                new DirectExchange("x.post.work", true, false, null),
                new DirectExchange("x.post.dead", true, false, null),

                new Queue("q.get.exercise.work", true, false, false, getArgs),
                new Queue("q.get.note.work", true, false, false, getArgs),
                new Queue("q.get.word.work", true, false, false, getArgs),
                new Queue("q.get.grammar.work", true, false, false, getArgs),
                new Queue("q.get.user.work", true, false, false, getArgs),

                new Queue("q.get.exercise.dead"),
                new Queue("q.get.note.dead"),
                new Queue("q.get.word.dead"),
                new Queue("q.get.grammar.dead"),
                new Queue("q.get.user.dead"),

                new Binding("q.get.exercise.work",Binding.DestinationType.QUEUE,
                        "x.get.work", "Exercise", null),
                new Binding("q.get.note.work",Binding.DestinationType.QUEUE,
                        "x.get.work", "Note", null),
                new Binding("q.get.word.work",Binding.DestinationType.QUEUE,
                        "x.get.work", "Word", null),
                new Binding("q.get.grammar.work",Binding.DestinationType.QUEUE,
                        "x.get.work", "Grammar", null),
                new Binding("q.get.user.work",Binding.DestinationType.QUEUE,
                        "x.get.work", "User", null),

                new Binding("q.get.exercise.dead",Binding.DestinationType.QUEUE,
                        "x.get.dead", "Exercise", null),
                new Binding("q.get.note.dead",Binding.DestinationType.QUEUE,
                        "x.get.dead", "Note", null),
                new Binding("q.get.word.dead",Binding.DestinationType.QUEUE,
                        "x.get.dead", "Word", null),
                new Binding("q.get.grammar.dead",Binding.DestinationType.QUEUE,
                        "x.get.dead", "Grammar", null),
                new Binding("q.get.user.dead",Binding.DestinationType.QUEUE,
                        "x.get.dead", "User", null),

                new Queue("q.post.exercise.work", true, false, false, postArgs),
                new Queue("q.post.note.work", true, false, false, postArgs),
                new Queue("q.post.word.work", true, false, false, postArgs),
                new Queue("q.post.grammar.work", true, false, false, postArgs),

                new Queue("q.post.exercise.dead"),
                new Queue("q.post.note.dead"),
                new Queue("q.post.word.dead"),
                new Queue("q.post.grammar.dead"),

                new Binding("q.post.exercise.work",Binding.DestinationType.QUEUE,
                        "x.post.work", "Exercise", null),
                new Binding("q.post.note.work",Binding.DestinationType.QUEUE,
                        "x.post.work", "Note", null),
                new Binding("q.post.word.work",Binding.DestinationType.QUEUE,
                        "x.post.work", "Word", null),
                new Binding("q.post.grammar.work",Binding.DestinationType.QUEUE,
                        "x.post.work", "Grammar", null),

                new Binding("q.post.exercise.dead",Binding.DestinationType.QUEUE,
                        "x.post.dead", "Exercise", null),
                new Binding("q.post.note.dead",Binding.DestinationType.QUEUE,
                        "x.post.dead", "Note", null),
                new Binding("q.post.word.dead",Binding.DestinationType.QUEUE,
                        "x.post.dead", "Word", null),
                new Binding("q.post.grammar.dead",Binding.DestinationType.QUEUE,
                        "x.post.dead", "Grammar", null)
        );
    }
}
