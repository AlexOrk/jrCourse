package jr_course.dao;

import jr_course.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

//    public List<User> findAll();
    public User findById(int id);
    public List<User> findAllByAdminFalse();
    public List<User> findByUsernameContainsAndAdminFalseOrFirstnameContainsAndAdminFalseOrLastnameContainsAndAdminFalseAllIgnoreCase(
            String username, String firstName, String lastName);

    public void deleteAllByAdminFalse();
}
