package jr_course.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="word")
public class Word {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name="id")
	private int id;

	@Column(name="level")
	private int level;

	@Column(name="jp_kanji")
	private String jpKanji;

	@Column(name="jp_kana")
	private String jpKana;

	@Column(name="ru_word")
	private String ruWord;

	@Column(name="description")
	private String description;

	@ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE,
												   CascadeType.DETACH, CascadeType.REFRESH})
	@JoinTable(name = "word_user",
			joinColumns = @JoinColumn(name = "word_id"),
			inverseJoinColumns = @JoinColumn(name = "user_id"))
	private List<User> userCollection;


	public Word() {}

	public Word(int level, String jpKanji, String jpKana, String ruWord, String description,
				List<User> userCollection) {
		this.level = level;
		this.jpKanji = jpKanji;
		this.jpKana = jpKana;
		this.ruWord = ruWord;
		this.description = description;
		this.userCollection = userCollection;
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

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
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











