package jr_course.dao;

import jr_course.entity.Grammar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GrammarRepository extends JpaRepository<Grammar, Integer> {
    public List<Grammar> findAll();
    public List<Grammar> findAllByLevel(int level);

}
