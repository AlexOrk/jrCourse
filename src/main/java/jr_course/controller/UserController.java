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
    public List<User> showUsers(@RequestParam("adminId") int adminId) {
        logger.info("\"/users/showUsers?adminId=" + adminId + "\"");

        return userService.findAll(adminId);
    }

    @DeleteMapping("/delete")
    public List<User> deleteUser(@RequestParam("userId") int userId, @RequestParam("adminId") int adminId) {
        logger.info("\"/users/deleteUser?userId=" + userId + "&adminId" + adminId + "\"");

        userService.deleteById(userId);

        logger.info("User was deleted!");
        logger.info("Return all users.");
        return userService.findAll(adminId);
    }

    @DeleteMapping("/deleteAll")
    public List<User> deleteAllUsers(@RequestParam("adminId") int adminId) {
        logger.info("\"/users/deleteAllUsers?adminId=" + adminId + "\"");

        userService.deleteAllExceptAdmin(adminId);

        logger.info("Return all users.");
        return userService.findAll(adminId);
    }

    @GetMapping("/search")
    public List<User> searchUser(@RequestParam(value = "user", required = false) String user,
                         @RequestParam("adminId") int adminId) {
        logger.info("\"/users/searchUser?user=" + user + "&adminId=" + adminId + "\"");

        if (user == null || user.trim().isEmpty()) {
            logger.info("Return all users.");
            return userService.findAll(adminId);
        }

        logger.info("Return user.");
        return userService.findUser(user);
    }
}
/*
Вывести всех юзеров кроме админа +
Удалить юзера по id +
Удалить всех юзеров кроме админа +
Найти юзера по id +
 */