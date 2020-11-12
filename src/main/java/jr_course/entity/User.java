package jr_course.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name="user")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="username")
    private String username;

    @Column(name="first_name")
    private String firstname;

    @Column(name="last_name")
    private String lastname;

    @Column(name="mail")
    private String mail;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                                                   CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(name = "word_user",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "word_id"))
    @JsonIgnore
    private Collection<Word> wordCollection;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                                                   CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(name = "grammar_user",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "grammar_id"))
    @JsonIgnore
    private Collection<Grammar> grammarCollection;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private Collection<Note> noteCollection;

    public User() {}

    public User(String username, String firstname, String lastname, String mail) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.mail = mail;
    }

    public User(String username, String firstname, String lastname, String mail,
                Collection<Word> wordCollection, Collection<Grammar> grammarCollection, Collection<Note> noteCollection) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.mail = mail;
        this.wordCollection = wordCollection;
        this.grammarCollection = grammarCollection;
        this.noteCollection = noteCollection;
    }

    public void addWordCollection(Word word) {
        if (wordCollection == null)
            wordCollection = new ArrayList<>();
        wordCollection.add(word);
    }

    public void addGrammarCollection(Grammar grammar) {
        if (grammarCollection == null)
            grammarCollection = new ArrayList<>();
        grammarCollection.add(grammar);
    }

    public void addNoteCollection(Note note) {
        if (noteCollection == null)
            noteCollection = new ArrayList<>();
        noteCollection.add(note);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Collection<Word> getWordCollection() {
        return wordCollection;
    }

    public void setWordCollection(Collection<Word> wordCollection) {
        this.wordCollection = wordCollection;
    }

    public Collection<Grammar> getGrammarCollection() {
        return grammarCollection;
    }

    public void setGrammarCollection(Collection<Grammar> grammarCollection) {
        this.grammarCollection = grammarCollection;
    }

    public Collection<Note> getNoteCollection() {
        return noteCollection;
    }

    public void setNoteCollection(Collection<Note> noteCollection) {
        this.noteCollection = noteCollection;
    }
}
