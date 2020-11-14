package jr_course.service;

import jr_course.entity.Note;

import java.util.List;

public interface NoteService {

    public List<Note> findAllByUserId(int id);

    public void save(Note note);

    public void deleteById(int id);
}
