package jr_course.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name="user")
@JacksonXmlRootElement(localName = "user")
@JsonPropertyOrder({"id", "username", "firstname", "lastname", "mail", "admin", "noteCollection"})
@ApiModel(description = "Details about the user")
public class User implements Serializable {

    @ApiModelProperty(notes = "The unique id of the user")
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @ApiModelProperty(notes = "The username the user is using")
    @Column(name="username")
    private String username;

    @ApiModelProperty(notes = "The user's first name")
    @Column(name="first_name")
    private String firstname;

    @ApiModelProperty(notes = "The user's last name")
    @Column(name="last_name")
    private String lastname;

    @ApiModelProperty(notes = "The user's mail address")
    @Column(name="mail")
    private String mail;

    @ApiModelProperty(notes = "The user's admin status")
    @Column(name="is_admin")
    private boolean admin;

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

    @ApiModelProperty(notes = "The collection of user notes")
    @JacksonXmlElementWrapper(localName = "noteCollection")
    @JacksonXmlProperty(localName = "note")
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Collection<Note> noteCollection;

    public User() {}

    public User(String username, String firstname, String lastname, String mail, boolean admin) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.mail = mail;
        this.admin = admin;
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

    public boolean getAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        admin = admin;
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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username +
                ", firstname='" + firstname +
                ", lastname='" + lastname +
                ", mail='" + mail +
                ", admin=" + admin +
                ", noteCollection=" + noteCollection +
                '}';
    }
}