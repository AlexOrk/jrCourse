package jr_course.service;

import jr_course.entity.Note;

import java.util.List;

public interface NoteService {

    public List<Note> findAllByUser_Id(int id);

    public void save(Note employee);

    public void deleteById(int id);
}
