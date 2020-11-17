package jr_course.service;

import jr_course.dao.NoteRepository;
import jr_course.dao.UserRepository;
import jr_course.entity.Note;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jr_course.exception.*;

import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {
    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    private NoteRepository noteRepository;
    private UserRepository userRepository;

    @Autowired
    public NoteServiceImpl(NoteRepository noteRepository, UserRepository userRepositor) {
        this.noteRepository = noteRepository;
        this.userRepository = userRepositor;
    }

    @Override
    public List<Note> findAllByUserId(int id) {
        logger.info("\"findAllByUserId(id)\"");
        logger.info("Find all words by user id " + id + ".");
        if (!userRepository.existsById(id))
            throw new DataNotFoundException("User with id " + id + " was not found.");
        return noteRepository.findAllByUser_Id(id);
    }

    @Override
    public void save(Note note) {
        logger.info("\"save(note)\"");
        logger.info("Save a note " + note + ".");
        noteRepository.save(note);
    }

    @Override
    public void deleteById(int id) {
        logger.info("\"deleteById(id)\"");
        logger.info("Delete a note by id " + id + ".");
        if (noteRepository.existsById(id)) {
            noteRepository.deleteById(id);
            logger.info("Note was deleted!");
        } else throw new DataNotFoundException("Note with id " + id + " was not found.");
    }
}
