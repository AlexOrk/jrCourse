package jr_course.service;

import jr_course.dao.NoteRepository;
import jr_course.dao.UserRepository;
import jr_course.entity.Note;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import jr_course.exception.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class NoteServiceTest {

    @InjectMocks
    private NoteServiceImpl noteService;

    @Mock
    private NoteRepository noteRepository;
    @Mock
    private UserRepository userRepository;

    @Test
    public void findAllByUserId_test() {
        Note note1 = new Note();
        Note note2 = new Note();

        when(noteRepository.findAllByUser_Id(1)).thenReturn(Arrays.asList(note1, note2));
        when(userRepository.existsById(1)).thenReturn(true);
        List<Note> notes = noteService.findAllByUserId(1);

        assertTrue(notes.contains(note1) && notes.contains(note2));

        when(userRepository.existsById(1)).thenReturn(false);

        assertEquals(DataNotFoundException.class, DataNotFoundException.class);
    }
}
