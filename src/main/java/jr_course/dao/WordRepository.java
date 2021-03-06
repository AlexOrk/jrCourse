package jr_course.dao;

import jr_course.entity.Word;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WordRepository extends JpaRepository<Word, Integer> {

	public List<Word> findAll();
	public List<Word> findAllByLevel(String level);
	public List<Word> findAllByUserCollection_Id(int id);
	public List<Word> findByJpKanjiContainsOrJpKanaContainsOrRuWordContainsAllIgnoreCase(
			String kanji, String kana, String ru);
//	public List<Word> findByUserCollection_IdAndJpKanjiContainsOrUserCollection_IdAndJpKanaContainsOrUserCollection_IdAndRuWordContainsAllIgnoreCase(
//			int id, String kanji, String kana, String ru);
}
