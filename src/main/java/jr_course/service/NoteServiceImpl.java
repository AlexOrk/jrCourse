package jr_course.service;

import jr_course.dao.NoteRepository;
import jr_course.entity.Note;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {
    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    private NoteRepository noteRepository;

    @Autowired
    public NoteServiceImpl(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @Override
    public List<Note> findAllByUser_Id(int id) {
        logger.info("\"findAllByUser_Id(id)\"");
        logger.info("Find all words by user id " + id + ".");
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
        noteRepository.deleteById(id);
    }
}
