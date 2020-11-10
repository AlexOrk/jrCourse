package jr_course.service;

import jr_course.dao.GrammarRepository;
import jr_course.entity.Grammar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GrammarServiceImpl implements GrammarService {

    private Logger logger = LoggerFactory.getLogger(GrammarServiceImpl.class);
    private GrammarRepository grammarRepository;

    @Autowired
    public GrammarServiceImpl(GrammarRepository grammarRepository) {
        this.grammarRepository = grammarRepository;
    }

    @Override
    public List<Grammar> findAll() {
        logger.info("\"findAll()\"");
        logger.info("Find all grammar in DB.");
        return grammarRepository.findAll();
    }
}
