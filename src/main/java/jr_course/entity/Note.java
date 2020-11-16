package jr_course.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="note")
@JacksonXmlRootElement(localName = "note")
@JsonPropertyOrder({"id", "name", "content"})
@ApiModel(description = "Details about user note")
public class Note implements Serializable {

	@ApiModelProperty(notes = "The unique id of the note")
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name="id")
	private int id;

	@ApiModelProperty(notes = "The note's name")
	@Column(name="name")
	private String name;

	@ApiModelProperty(notes = "The note's content")
	@Column(name="content")
	private String content;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	@JsonIgnore
	private User user;

	public Note() {}

	public Note(String name, String content) {
		this.name = name;
		this.content = content;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Note{" +
				"id=" + id +
				", name=" + name +
				", content='" + content + '\'' +
				", user=" + user +
				'}';
	}
}











