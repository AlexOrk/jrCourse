package jr_course.dao;

import jr_course.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    public List<User> findAll();
    public User findById(int id);
    public List<User> findByUsernameContainsOrFirstnameContainsOrLastnameContainsAllIgnoreCase(
            String username, String firstName, String lastName);

    @Transactional
    @Modifying
    @Query(value = "delete from User u where not u.id = :id")
    public void deleteAllExceptAdmin(int id);

}
