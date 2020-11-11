package jr_course.dao;

import jr_course.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Integer> {

    public List<Note> findAllByUser_Id(int id);
}
