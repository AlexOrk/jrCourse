package jr_course.service;

import jr_course.dao.WordRepository;
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

	private Logger logger = LoggerFactory.getLogger(WordServiceImpl.class);
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

}






