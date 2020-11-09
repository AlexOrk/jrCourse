package jr_course.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name="grammar")
public class Grammar {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="level")
    private int level;

    @Column(name="formula")
    private String formula;

    @Column(name="example")
    private String example;

    @Column(name="description")
    private String description;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                                                   CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(name = "grammar_user",
            joinColumns = @JoinColumn(name = "grammar_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Collection<User> userCollection;

    @OneToMany(mappedBy = "grammar", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    Collection<Exercise> exerciseCollection;

    public Grammar() {}

    public Grammar(int level, String formula, String example, String description,
                   Collection<User> userCollection, Collection<Exercise> exerciseCollection) {
        this.level = level;
        this.formula = formula;
        this.example = example;
        this.description = description;
        this.userCollection = userCollection;
        this.exerciseCollection = exerciseCollection;
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
