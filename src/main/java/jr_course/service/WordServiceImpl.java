package jr_course.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jr_course.dao.WordRepository;
import jr_course.entity.User;
import jr_course.entity.Word;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jr_course.exception.*;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.Optional;

@Service
public class WordServiceImpl implements WordService {

	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	private WordRepository wordRepository;
	
	@Autowired
	public WordServiceImpl(WordRepository wordRepository) {
		this.wordRepository = wordRepository;
	}
	
	@Override
	public List<Word> findAll() {
		logger.info("\"findAll()\"");
		logger.info("Find all words in DB.");
		return wordRepository.findAll();
	}

	@Override
	public List<Word> findAllByLevel(String level) {
		logger.info("\"findByLevelContains(level)\"");
		logger.info("Find all words in DB depending on the \"" + level + "\" level.");
		if (!(level.equals("easy") || level.equals("medium") || level.equals("hard")))
			throw new IncorrectDataInputException("Incorrect level data input - " + level);

		return wordRepository.findAllByLevel(level);
	}

	@Override
	public Word findById(int id) {
		logger.info("\"findById(id)\"");
		logger.info("Find word by id " + id + ".");
		Optional<Word> result = wordRepository.findById(id);

		Word word = null;

		if (result.isPresent()) {
			word = result.get();
		}
		else {
			logger.warn("Word was not found.");
			throw new DataNotFoundException("Word with id " + id + " was not found.");
		}

		logger.info("Return word.");
		return word;
	}

	@Override
	public Word save(String newWord) {
		logger.info("\"save(newWord)\"");
		logger.info("Save a word " + newWord + ".");

		JSONObject root = new JSONObject(newWord);
		String level = root.getString("level");
		String jpKanji = root.getString("jpKanji");
		String jpKana = root.getString("jpKana");
		String ruWord = root.getString("ruWord");
		String description = root.getString("description");

		if (!(level.equals("easy") || level.equals("medium")
				|| level.equals("hard")))
			throw new IncorrectDataInputException("Incorrect level data input - " + level);

		if (!jpKanji.isEmpty() && !jpKanji.matches("^[\\p{sc=Han}\\p{sc=Hiragana}\\p{sc=Katakana}]+$"))
			throw new IncorrectDataInputException("Incorrect jpKanji data input - " + jpKanji);

		if (!jpKana.matches("^[\\p{sc=Hiragana}\\p{sc=Katakana}]+$"))
			throw new IncorrectDataInputException("Incorrect jpKana data input - " + jpKana);

		if (!ruWord.matches("^([а-яА-Я0-9()/.,-]+(\\s)?)+$"))
			throw new IncorrectDataInputException("Incorrect ruWord data input - " + ruWord);

		if (description.trim().isEmpty())
			throw new IncorrectDataInputException("Incorrect description data input - " + description);

		ObjectMapper mapper = new ObjectMapper();
		StringReader reader = new StringReader(newWord);

		Word word = null;
		try {
			word = mapper.readValue(reader, Word.class);
			logger.info("Word was read.");
		} catch (IOException e) {
			logger.debug(e.getMessage());
			logger.debug("Incorrect data input - " + newWord);
		}

		wordRepository.save(word);

		logger.info("Word was saved!");
		return word;
	}

	@Override
	public void deleteById(int id) {
		logger.info("\"deleteById(id)\"");
		logger.info("Delete a word by id " + id + ".");
		if(wordRepository.existsById(id)) wordRepository.deleteById(id);
		else throw new DataNotFoundException("Word with id " + id + " was not found.");
	}

	@Override
	public List<Word> findAllByUserId(int userId) {
		logger.info("\"findAllByUserCollection_Id(userId)\"");
		logger.info("Find all words by user id " + userId + ".");
		return wordRepository.findAllByUserCollection_Id(userId);
	}

	public void addWordToPersonalVocabulary(User user, int wordId) {
		logger.info("\"addWordToPersonalVocabulary(user, wordId)\"");
		logger.info("Add word to personal vocabulary");

		Word word = findById(wordId);

		if (word.getUserCollection().contains(user)) {
			logger.info("Word was already added!");
		} else {
			logger.info("Add word.");
			word.addUser(user);

			logger.info("Update word.");
			wordRepository.save(word);
			logger.info("Word was added to personal vocabulary.");
		}
	}

	@Override
	public void deleteWordFromPersonalVocabulary(int wordId, User user) {
		logger.info("\"deleteWordFromPersonal(wordId, user)\"");
		logger.info("Delete word by id " + wordId + "from personal vocabulary.");

		logger.info("Find word by id and delete user.");
		Word word = findById(wordId);

		if (word.getUserCollection().contains(user)) {
			word.deleteUser(user);

			logger.info("Update word.");
			wordRepository.save(word);
			logger.info("Word was deleted from personal vocabulary!");
		} else logger.info("Word with id + " + wordId + " was not found in the personal list." );
	}

	@Override
	public List<Word> findByDifferentParameters(String param) {
		logger.info("\"findByDifferentParameters(param)\"");
		logger.info("Find words by parameter " + param + ".");

		param = param.trim();
		List<Word> wordList = wordRepository
				.findByJpKanjiContainsOrJpKanaContainsOrRuWordContainsAllIgnoreCase(param, param, param);

		if (wordList.isEmpty()) logger.info("Word list is empty.");

		logger.info("Return word list.");
		return wordList;
	}
}






