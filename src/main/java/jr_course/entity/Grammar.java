package jr_course.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name="grammar")
@JacksonXmlRootElement(localName = "grammar")
@JsonPropertyOrder({"id", "level", "formula", "example", "description", "exerciseCollection"})
@ApiModel(description = "Details about grammar for learning japanese")
public class Grammar implements Serializable {

    @ApiModelProperty(notes = "The unique id of grammar")
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @ApiModelProperty(notes = "The grammar's level")
    @Column(name="level")
    @NotNull(message="is required")
    @Min(value = 1, message = "must be greater than or equal to one")
    @Max(value = 5, message = "must be less than or equal to five")
    private Integer level;

    @ApiModelProperty(notes = "The grammar's formula")
    @Column(name="formula")
    @NotNull(message="is required")
    @Size(max = 20, message = "is required")
    @Pattern(regexp = "^[\\p{sc=Han}\\p{sc=Hiragana}\\p{sc=Katakana}。、「」？！〜]+$",
            message = "Only kanji, hiragana or katakana")
    private String formula;

    @ApiModelProperty(notes = "The example of using grammar")
    @Column(name="example")
    @NotNull(message="is required")
    @Size(max = 150, message = "is required")
    @Pattern(regexp = "^[\\p{sc=Han}\\p{sc=Hiragana}\\p{sc=Katakana}a-zA-Z0-9.,?!()（）。、「」？！〜/\\-]+$",
            message = "Only kanji, hiragana, katakana or latin characters, 0-9 and symbols (.,?!()（）。、「」？！〜/-)")
    private String example;

    @ApiModelProperty(notes = "The grammar's description")
    @Column(name="description")
    @NotNull(message="is required")
    @Size(max = 300, message = "is required")
    private String description;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                                                   CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(name = "grammar_user",
            joinColumns = @JoinColumn(name = "grammar_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @JsonIgnore
    private Collection<User> userCollection;

    @ApiModelProperty(notes = "The collection of exercises for learning grammar")
    @JacksonXmlElementWrapper(localName = "exerciseCollection")
    @JacksonXmlProperty(localName = "exercise")
    @OneToMany(mappedBy = "grammar", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Collection<Exercise> exerciseCollection;

    public Grammar() {}

    public Grammar(int level, String formula, String example, String description) {
        this.level = level;
        this.formula = formula;
        this.example = example;
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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Collection<User> getUserCollection() {
        return userCollection;
    }

    public void setUserCollection(Collection<User> userCollection) {
        this.userCollection = userCollection;
    }

    public Collection<Exercise> getExerciseCollection() {
        return exerciseCollection;
    }

    public void setExerciseCollection(Collection<Exercise> exerciseCollection) {
        this.exerciseCollection = exerciseCollection;
    }

    @Override
    public String toString() {
        return "Grammar{" +
                "id=" + id +
                ", level=" + level +
                ", formula='" + formula + '\'' +
                ", example='" + example + '\'' +
                ", description='" + description + '\'' +
                ", userCollection=" + userCollection +
                ", exerciseCollection=" + exerciseCollection +
                '}';
    }
}
