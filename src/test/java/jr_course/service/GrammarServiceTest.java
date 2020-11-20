package jr_course.service;

import jr_course.dao.GrammarRepository;
import jr_course.entity.Grammar;
import jr_course.entity.Word;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import jr_course.exception.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GrammarServiceTest {

    @InjectMocks
    private GrammarServiceImpl grammarService;

    @Mock
    private GrammarRepository grammarRepository;

    @Test
    public void findAll_test() {
        Grammar grammar1 = new Grammar();
        Grammar grammar2 = new Grammar();

        when(grammarRepository.findAll()).thenReturn(Arrays.asList(grammar1, grammar2));
        List<Grammar> grammarList = grammarService.findAll();

        assertTrue(grammarList.contains(grammar1) && grammarList.contains(grammar2));

        when(grammarRepository.findAll()).thenReturn(new ArrayList<>());
        grammarList = grammarService.findAll();

        assertTrue(grammarList.isEmpty());
    }

    @Test
    public void findAllByLevel_test() {
        Grammar grammar1 = new Grammar();
        Grammar grammar2 = new Grammar();

        when(grammarRepository.findAllByLevel(1)).thenReturn(Arrays.asList(grammar1, grammar2));
        List<Grammar> grammarList = grammarService.findAllByLevel(1);

        assertTrue(grammarList.contains(grammar1) && grammarList.contains(grammar2));

        when(grammarRepository.findAllByLevel(1)).thenReturn(new ArrayList<>());
        grammarList = grammarService.findAllByLevel(1);

        assertTrue(grammarList.isEmpty());
    }

    @Test(expected = IncorrectDataInputException.class)
    public void findAllByLevel_ExceptionTest() {
        int lvl = (int) (Math.random() * (Integer.MAX_VALUE) + 6);
        grammarService.findAllByLevel(lvl);
    }

    @Test
    public void findById_test() {
        Grammar expectedGrammar = new Grammar();

        when(grammarRepository.findById(1)).thenReturn(java.util.Optional.of(expectedGrammar));
        Grammar actualGrammar = grammarService.findById(1);

        assertEquals(expectedGrammar, actualGrammar);
    }

    @Test
    public void findAllByUserId_test() {
        Grammar grammar1 = new Grammar();
        Grammar grammar2 = new Grammar();

        when(grammarRepository.findAllByUserCollection_Id(1)).thenReturn(Arrays.asList(grammar1, grammar2));
        List<Grammar> grammarList = grammarService.findAllByUserId(1);

        assertTrue(grammarList.contains(grammar1) && grammarList.contains(grammar2));

        when(grammarRepository.findAllByUserCollection_Id(1)).thenReturn(new ArrayList<>());
        grammarList = grammarService.findAllByUserId(1);

        assertTrue(grammarList.isEmpty());
    }

    @Test
    public void findByDifferentParameters_test() {
        String param = "  に限り ";
        Grammar grammar1 = new Grammar();
        Grammar grammar2 = new Grammar();

        when(grammarRepository.findByFormulaContainsOrExampleContainsAllIgnoreCase(param.trim(), param.trim()))
                .thenReturn(Arrays.asList(grammar1, grammar2));
        List<Grammar> grammarList = grammarService.findByDifferentParameters(param);

        assertTrue(grammarList.contains(grammar1) && grammarList.contains(grammar2));

        when(grammarRepository.findByFormulaContainsOrExampleContainsAllIgnoreCase(param.trim(), param.trim()))
                .thenReturn(new ArrayList<>());
        grammarList = grammarService.findByDifferentParameters(param);

        assertTrue(grammarList.isEmpty());
    }
}
