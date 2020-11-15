package jr_course.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="word")
@XmlRootElement(name = "word")
@XmlType(propOrder = {"id", "level", "jpKanji", "jpKana", "ruWord", "description"})
@JsonPropertyOrder({"id", "level", "jpKanji", "jpKana", "ruWord", "description"})
public class Word implements Serializable {

	@XmlAttribute
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name="id")
	private int id;

	@XmlElement
	@Column(name="level")
	private String level;

	@XmlElement
	@Column(name="jp_kanji")
	private String jpKanji;

	@XmlElement
	@Column(name="jp_kana")
	private String jpKana;

	@XmlElement
	@Column(name="ru_word")
	private String ruWord;

	@XmlElement
	@Column(name="description")
	private String description;

	@ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE,
												   CascadeType.DETACH, CascadeType.REFRESH})
	@JoinTable(name = "word_user",
			joinColumns = @JoinColumn(name = "word_id"),
			inverseJoinColumns = @JoinColumn(name = "user_id"))
	@JsonIgnore
	private List<User> userCollection;

	public Word() {}

	public Word(String level, String jpKanji, String jpKana, String ruWord, String description) {
		this.level = level;
		this.jpKanji = jpKanji;
		this.jpKana = jpKana;
		this.ruWord = ruWord;
		this.description = description;
	}

	public void addUser(User user) {
		if (userCollection == null)
			userCollection = new ArrayList<>();
		userCollection.add(user);
	}

	public void deleteUser(User user) {
		if (userCollection != null)
			userCollection.remove(user);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getJpKanji() {
		return jpKanji;
	}

	public void setJpKanji(String jpKanji) {
		this.jpKanji = jpKanji;
	}

	public String getJpKana() {
		return jpKana;
	}

	public void setJpKana(String jpKana) {
		this.jpKana = jpKana;
	}

	public String getRuWord() {
		return ruWord;
	}

	public void setRuWord(String ruWord) {
		this.ruWord = ruWord;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<User> getUserCollection() {
		return userCollection;
	}

	public void setUserCollection(List<User> userCollection) {
		this.userCollection = userCollection;
	}

	@Override
	public String toString() {
		return "Word{" +
				"id=" + id +
				", level=" + level +
				", jpKanji='" + jpKanji + '\'' +
				", jpKana='" + jpKana + '\'' +
				", ruWord='" + ruWord + '\'' +
				", description='" + description + '\'' +
				", userCollection=" + userCollection +
				'}';
	}
}











