package jr_course.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import jr_course.entity.User;
import jr_course.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/users", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
public class UserController {
    // Controller for admin section

    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // show all users except admin
    @GetMapping()
    @ApiOperation(value = "Show all users", notes = "Find all users except admin", response = List.class)
    public List<User> showUsers() {
        logger.info("\"/users\"");

        return userService.findAllExceptAdmin();
    }

    @DeleteMapping("/delete")
    @ApiOperation(value = "Delete user", notes = "Delete user by user id and return all users", response = List.class)
    public List<User> deleteUser(@ApiParam(value = "Id value for user you need to delete", required = true)
                                 @RequestParam("userId") int userId) {
        logger.info("\"/users/deleteUser?userId=" + userId + "\"");

        userService.deleteById(userId);

        logger.info("User was deleted!");
        logger.info("Return all users.");
        return userService.findAllExceptAdmin();
    }

    @DeleteMapping("/deleteAll")
    @ApiOperation(value = "Delete all users", notes = "Delete all users except admin", response = List.class)
    public List<User> deleteAllUsers() {
        logger.info("\"/users/deleteAllUsers\"");

        userService.deleteAllExceptAdmin();

        logger.info("Return all users.");
        return userService.findAllExceptAdmin();
    }

    @GetMapping("/search")
    @ApiOperation(value = "Search user by param",
            notes = "If param exists, find and return users by param, otherwise return all", response = List.class)
    public List<User> searchUser(@ApiParam(value = "Param value for user you need to find", required = true)
                                 @RequestParam(value = "param", required = false) String param) {
        logger.info("\"/users/searchUser?param=" + param + "\"");

        if (param == null || param.trim().isEmpty()) {
            logger.info("Return all users.");
            return userService.findAllExceptAdmin();
        }

        logger.info("Return found users.");
        return userService.findUsersByParam(param);
    }
}