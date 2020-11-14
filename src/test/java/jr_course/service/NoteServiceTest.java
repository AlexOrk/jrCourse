package jr_course.service;

import jr_course.dao.NoteRepository;
import jr_course.entity.Note;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class NoteServiceTest {

    @InjectMocks
    private NoteServiceImpl noteService;

    @Mock
    private NoteRepository noteRepository;

    @Test
    public void findAllByUserId_test() {
        Note note = new Note("Name", "Content");

        when(noteRepository.findAllByUser_Id(1)).thenReturn(Arrays.asList(note));

        List<Note> notes = noteService.findAllByUserId(1);

        assertTrue(notes.contains(note));
    }
}
