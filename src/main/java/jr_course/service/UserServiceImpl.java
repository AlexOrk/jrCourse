package jr_course.service;

import jr_course.dao.UserRepository;
import jr_course.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAllExceptAdmin() {
        logger.info("\"findAllExceptAdmin()\"");
        logger.info("Find all users except admin.");

        return userRepository.findAllByAdminFalse();
    }

    @Override
    public List<User> findUsersByParam(String param) {
        logger.info("\"findUsersByParam(param)\"");
        logger.info("Find users by username or first name or last name.");

        param = param.trim();
        List<User> user = userRepository
                .findByUsernameContainsAndAdminFalseOrFirstnameContainsAndAdminFalseOrLastnameContainsAndAdminFalseAllIgnoreCase(
                param, param, param);

        if (user.isEmpty()) logger.info("Users list is empty.");

        logger.info("Return users.");
        return user;
    }

    @Override
    public User findById(int id) {
        logger.info("\"findById(id)\"");
        logger.info("Find user by id " + id + ".");
        return userRepository.findById(id);
    }

    @Override
    public void deleteById(int id) {
        logger.info("\"deleteById(id)\"");
        logger.info("Delete user by id " + id + ".");
        userRepository.deleteById(id);
        logger.info("User was deleted!");
    }

    @Transactional
    @Modifying
    @Override
    public void deleteAllExceptAdmin() {
        logger.info("\"deleteAllExceptAdmin()\"");
        logger.info("Delete all users except admin.");
        userRepository.deleteAllByAdminFalse();
        logger.info("All users was deleted!");
    }
}
