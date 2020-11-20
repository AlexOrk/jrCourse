package jr_course.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name="exercise")
@JacksonXmlRootElement(localName = "exercise")
@JsonPropertyOrder({"id", "description", "task", "answer"})
@ApiModel(description = "Details about the exercise for learning grammar")
public class Exercise implements Serializable {

	@ApiModelProperty(notes = "The unique id of the exercise")
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name="id")
	private int id;

	@ApiModelProperty(notes = "The exercise's description")
	@Column(name="description")
	@NotNull(message="Is required")
	@Size(max = 150, message = "Is required")
	private String description;

	@ApiModelProperty(notes = "The exercise's task")
	@Column(name="task")
	@NotNull(message="Is required")
	@Size(max = 300, message = "Is required")
	private String task;

	@ApiModelProperty(notes = "The exercise's answer")
	@Column(name="answer")
	@NotNull(message="Is required")
	@Size(max = 150, message = "Is required")
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
				", description='" + description +
				", task='" + task +
				", answer='" + answer +
				", grammar=" + grammar +
				'}';
	}
}











