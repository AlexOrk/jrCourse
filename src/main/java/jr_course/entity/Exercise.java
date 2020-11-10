package jr_course.entity;

import javax.persistence.*;

@Entity
@Table(name="exercise")
public class Exercise {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name="id")
	private int id;

	@Column(name="description")
	private String description;

	@Column(name="task")
	private String task;

	@Column(name="answer")
	private String answer;

	@ManyToOne
	@JoinColumn(name = "grammar_id", nullable = false)
	private Grammar grammar;

	public Exercise() {}

	public Exercise(String description, String task, String answer, Grammar grammar) {
		this.description = description;
		this.task = task;
		this.answer = answer;
		this.grammar = grammar;
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











