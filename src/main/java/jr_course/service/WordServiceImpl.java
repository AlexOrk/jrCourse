package jr_course.service;

import jr_course.dao.WordRepository;
import jr_course.entity.User;
import jr_course.entity.Word;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
			logger.warn("Word not found.");
			throw new RuntimeException("Слово с id - " + id + " не было найдено.");
		}

		logger.info("Return word.");
		return word;
	}

	@Override
	public void save(Word word) {
		logger.info("\"save(word)\"");
		logger.info("Save a word " + word + ".");
		wordRepository.save(word);
	}

	@Override
	public void deleteById(int id) {
		logger.info("\"deleteById(id)\"");
		logger.info("Delete a word in DB by id " + id + ".");
		wordRepository.deleteById(id);
	}

	@Override
	public List<Word> findAllByUserCollection_Id(int userId) {
		logger.info("\"findAllByUserCollection_Id(userId)\"");
		logger.info("Find all words by user id " + userId + ".");
		return wordRepository.findAllByUserCollection_Id(userId);
	}

	@Override
	public List<Word> findAllWordsInPersonalVocabulary(int userId, String word) {
		logger.info("\"findAllWordsInPersonalVocabulary\"");
		logger.info("Searching for words in the personal list.");

		List<Word> personalWordList = wordRepository.findAllByUserCollection_Id(userId);
		List<Word> wordList = new ArrayList<>();

		for (Word w : personalWordList) {
			if (w.getJpKanji().contains(word) || w.getJpKana().contains(word) ||
					w.getRuWord().contains(word)) {
				wordList.add(w);
			}
		}

		if (wordList.isEmpty()) {
			logger.info("wordList is empty.");
		}
		logger.info("Return wordList.");
		return wordList;
	}

	public void addWordInPersonalVocabulary(User user, int wordId) {
		logger.info("\"addWordInPersonalVocabulary(user, wordId)\"");
		logger.info("Add word in personal vocabulary");

		Word word = findById(wordId);

		logger.info("Add word.");
		word.addUser(user);

		logger.info("Update word.");
		wordRepository.save(word);
		logger.info("Word was added in personal vocabulary.");
	}

	@Override
	public void deleteWordFromPersonalVocabulary(int wordId, User user) {
		logger.info("\"deleteWordFromPersonal(wordId, user)\"");
		logger.info("Delete word by id " + wordId + "from personal vocabulary.");

		logger.info("Find word by id and delete user.");
		Word word = findById(wordId);
		word.deleteUser(user);

		logger.info("Update word.");
		wordRepository.save(word);
		logger.info("Word was deleted from personal vocabulary!");
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

	@Override
	public List<Word> findAllByParamAndLevel(String param, String level) {
		logger.info("\"findAllByParamAndLevel(param, level)\"");
		logger.info("Find all words by parameter and level.");

		param = param.trim();
		logger.info("Find words by level.");
		List<Word> wordList = wordRepository.findAllByLevel(level);

		if (wordList.isEmpty()) {
			logger.info("Word list is empty.");
			return wordList;
		}

		logger.info("Find words by parameter.");
		List<Word> foundedListByWordAndLevel = new ArrayList<>();
		for (Word w : wordList) {
			if (w.getJpKanji().contains(param) || w.getJpKana().contains(param) || w.getRuWord().contains(param)) {
				foundedListByWordAndLevel.add(w);
			}
		}

		// Maybe this logic is faster?
		// 1. Find by param
		// 2. Find by level

		// or use public List<Word> findByJpKanjiContainsOrJpKanaContainsOrRuWordContainsAndLevelAllIgnoreCase(
		//			String kanji, String kana, String ru, String level);

//		logger.info("Find words by parameter.");
//		param = param.trim();
//		List<Word> wordList = wordRepository.findByJpKanjiContainsOrJpKanaContainsOrRuWordContainsAllIgnoreCase(
//				param, param, param);
//
//		if (wordList.isEmpty()) {
//			logger.info("Word list is empty.");
//			return wordList;
//		}
//
//		logger.info("Find words by level.");
//		List<Word> foundedListByWordAndLevel = new ArrayList<>();
//		for (Word w : wordList) {
//			if (w.getLevel().equals(level))
//				foundedListByWordAndLevel.add(w);
//		}

		if (foundedListByWordAndLevel.isEmpty()) logger.info("Founded word list is empty.");

		logger.info("Return word list.");
		return foundedListByWordAndLevel;
	}
}






