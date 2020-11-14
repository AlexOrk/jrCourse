package jr_course.controller;

import jr_course.entity.User;
import jr_course.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    // Controller for admin section

    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // show all users except admin
    @GetMapping("/showUsers")
    public List<User> showUsers() {
        logger.info("\"/users/showUsers\"");

        return userService.findAllExceptAdmin();
    }

    @DeleteMapping("/delete")
    public List<User> deleteUser(@RequestParam("userId") int userId) {
        logger.info("\"/users/deleteUser?userId=" + userId + "\"");

        userService.deleteById(userId);

        logger.info("User was deleted!");
        logger.info("Return all users.");
        return userService.findAllExceptAdmin();
    }

    @DeleteMapping("/deleteAll")
    public List<User> deleteAllUsers() {
        logger.info("\"/users/deleteAllUsers\"");

        userService.deleteAllExceptAdmin();

        logger.info("Return all users.");
        return userService.findAllExceptAdmin();
    }

    @GetMapping("/search")
    public List<User> searchUser(@RequestParam(value = "param", required = false) String param) {
        logger.info("\"/users/searchUser?param=" + param + "\"");

        if (param == null || param.trim().isEmpty()) {
            logger.info("Return all users.");
            return userService.findAllExceptAdmin();
        }

        logger.info("Return found users.");
        return userService.findUsersByParam(param);
    }
}
/*
Вывести всех юзеров кроме админа +
Удалить юзера по id +
Удалить всех юзеров кроме админа +
Найти юзера по id +
 */