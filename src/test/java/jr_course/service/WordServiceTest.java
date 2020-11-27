package jr_course.service;

import jr_course.dao.WordRepository;
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

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WordServiceTest {

    @InjectMocks
    private WordServiceImpl wordService;

    @Mock
    private WordRepository wordRepository;

    @Test
    public void findAll_test() {
        Word word1 = new Word();
        Word word2 = new Word();

        when(wordRepository.findAll()).thenReturn(Arrays.asList(word1, word2));
        List<Word> words = wordService.findAll();

        assertTrue(words.contains(word1) && words.contains(word2));

        when(wordRepository.findAll()).thenReturn(new ArrayList<>());
        words = wordService.findAll();

        assertTrue(words.isEmpty());
    }

    @Test
    public void findAllByLevel_test() {
        String level = "easy";
        Word word1 = new Word();
        Word word2 = new Word();

        when(wordRepository.findAllByLevel(level)).thenReturn(Arrays.asList(word1, word2));
        List<Word> words = wordService.findAllByLevel(level);

        assertTrue(words.contains(word1) && words.contains(word2));

        when(wordRepository.findAllByLevel(level)).thenReturn(new ArrayList<>());
        words = wordService.findAllByLevel(level);

        assertTrue(words.isEmpty());
    }

    @Test(expected = IncorrectDataInputException.class)
    public void findAllByLevel_ExceptionTest() {
        String level = "ff";
        wordService.findAllByLevel(level);
    }

    @Test
    public void findById_test() {
        Word expectedWord = new Word();

        when(wordRepository.findById(1)).thenReturn(java.util.Optional.of(expectedWord));
        Word actualWord = wordService.findById(1);

        assertEquals(expectedWord, actualWord);
    }

    @Test
    public void findAllByUserId_test() {
        Word word1 = new Word();
        Word word2 = new Word();

        when(wordRepository.findAllByUserCollection_Id(1)).thenReturn(Arrays.asList(word1, word2));
        List<Word> words = wordService.findAllByUserId(1);

        assertTrue(words.contains(word1) && words.contains(word2));

        when(wordRepository.findAllByUserCollection_Id(1)).thenReturn(new ArrayList<>());
        words = wordService.findAllByUserId(1);

        assertTrue(words.isEmpty());
    }

    @Test
    public void findByDifferentParameters_test() {
        String param = "  株式会社 ";
        Word word1 = new Word();
        Word word2 = new Word();

        when(wordRepository.findByJpKanjiContainsOrJpKanaContainsOrRuWordContainsAllIgnoreCase(
                param.trim(), param.trim(), param.trim())).thenReturn(Arrays.asList(word1, word2));
        List<Word> words = wordService.findByDifferentParameters(param);

        assertTrue(words.contains(word1) && words.contains(word2));

        when(wordRepository.findByJpKanjiContainsOrJpKanaContainsOrRuWordContainsAllIgnoreCase(
                param.trim(), param.trim(), param.trim())).thenReturn(new ArrayList<>());
        words = wordService.findByDifferentParameters(param);

        assertTrue(words.isEmpty());
    }
}
