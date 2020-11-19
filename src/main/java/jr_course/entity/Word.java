package jr_course.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="word")
@JacksonXmlRootElement(localName = "word")
@JsonPropertyOrder({"id", "level", "jpKanji", "jpKana", "ruWord", "description"})
@ApiModel(description = "Details about the word for learning japanese")
public class Word implements Serializable {

	@ApiModelProperty(notes = "The unique id of the word")
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name="id")
	private int id;

	@ApiModelProperty(notes = "The word's level")
	@Column(name="level")
	@NotNull(message="is required")
	@Pattern(regexp = "^easy$|^medium$|^hard$", message = "could be easy, medium or hard")
	private String level;

	@ApiModelProperty(notes = "The word written in Japanese character (kanji)")
	@Column(name="jp_kanji")
	@Size(max = 10, message = "is required")
	@Pattern(regexp = "^[\\p{sc=Han}\\p{sc=Hiragana}\\p{sc=Katakana}]+$",
			message = "Only kanji, hiragana or katakana")
	private String jpKanji;

	@ApiModelProperty(notes = "The word written in Japanese alphabet (hiragana or katakana)")
	@Column(name="jp_kana")
	@NotNull(message="is required")
	@Size(max = 20, message = "is required")
	@Pattern(regexp = "^[\\p{sc=Hiragana}\\p{sc=Katakana}]+$",
			message = "Only hiragana or katakana")
	private String jpKana;

	@ApiModelProperty(notes = "The word written in Russian")
	@Column(name="ru_word")
	@NotNull(message="is required")
	@Size(max = 20, message = "is required")
	@Pattern(regexp = "^([а-яА-Я0-9()/.,\\-!?]+(\\s)?)+$",
			message = "Only cyrillic characters, 0-9 and symbols ()/.,-")
	private String ruWord;

	@ApiModelProperty(notes = "The word's description")
	@Column(name="description")
	@NotNull(message="is required")
	@Size(max = 150, message = "is required")
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
				", jpKanji='" + jpKanji +
				", jpKana='" + jpKana +
				", ruWord='" + ruWord +
				", description='" + description +
				'}';
	}
}











