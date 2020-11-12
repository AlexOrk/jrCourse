package jr_course.service;

import jr_course.dao.UserRepository;
import jr_course.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<User> findAll(int adminId) {
        logger.info("\"findAll(adminId)\"");
        logger.info("Find all users except admin.");
        List<User> users = userRepository.findAll();

        User admin = userRepository.findById(adminId);

        // delete admin from list of users
        users.removeIf(nextUser -> nextUser.getUsername().equals(admin.getUsername()));
        logger.info("Return all users except admin.");
        return users;
    }

    @Override
    public List<User> findUser(String name) {
        logger.info("\"findUser(name)\"");
        logger.info("Find users by username or first name or last name.");

        name = name.trim();
        List<User> user = userRepository.findByUsernameContainsOrFirstnameContainsOrLastnameContainsAllIgnoreCase(
                    name, name, name);

        if (user.isEmpty()) {
            logger.info("Users list is empty.");
        }
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
    }

    @Override
    public void deleteAllExceptAdmin(int adminId) {
        logger.info("\"deleteAllExceptAdmin(adminId)\"");
        logger.info("Delete all users except admin.");
        userRepository.deleteAllExceptAdmin(adminId);
    }
}
