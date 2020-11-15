package jr_course.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;

@Entity
@Table(name="exercise")
@XmlRootElement(name = "exercise")
@XmlType(propOrder = {"id", "description", "task", "answer"})
@JsonPropertyOrder({"id", "description", "task", "answer"})
public class Exercise implements Serializable {

	@XmlAttribute
	@XmlID
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name="id")
	private int id;

	@XmlElement
	@Column(name="description")
	private String description;

	@XmlElement
	@Column(name="task")
	private String task;

	@XmlElement
	@Column(name="answer")
	private String answer;

	@ManyToOne
	@JoinColumn(name = "grammar_id", nullable = false)
	@JsonIgnore
	private Grammar grammar;

	public Exercise() {}

	public Exercise(String description, String task, String answer) {
		this.description = description;
		this.task = task;
		this.answer = answer;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public Grammar getGrammar() {
		return grammar;
	}

	public void setGrammar(Grammar grammar) {
		this.grammar = grammar;
	}

	@Override
	public String toString() {
		return "Exercise{" +
				"id=" + id +
				", description='" + description + '\'' +
				", task='" + task + '\'' +
				", answer='" + answer + '\'' +
				", grammar=" + grammar +
				'}';
	}
}











