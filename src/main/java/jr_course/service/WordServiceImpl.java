package jr_course.service;

import jr_course.dao.WordRepository;
import jr_course.entity.Word;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public List<Word> findByLevelContains(String level) {
		logger.info("\"findByLevelContains(level)\"");
		logger.info("Find all words in DB depending on the \"" + level + "\" level.");
		return wordRepository.findAllByLevelContains(level);
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

}






