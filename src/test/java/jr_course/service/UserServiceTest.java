package jr_course.service;

import jr_course.dao.UserRepository;
import jr_course.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import jr_course.exception.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Test
    public void findAllExceptAdmin_test() {
        User user1 = new User();
        User user2 = new User();

        when(userRepository.findAllByAdminFalse()).thenReturn(Arrays.asList(user1, user2));
        List<User> users = userService.findAllExceptAdmin();

        assertTrue(users.contains(user1) && users.contains(user2));

        when(userRepository.findAllByAdminFalse()).thenReturn(new ArrayList<>());
        users = userService.findAllExceptAdmin();

        assertTrue(users.isEmpty());
    }

    @Test
    public void findUsersByParam_test() {
        String param = "o";
        User user1 = new User();
        User user2 = new User();

        when(userRepository
                .findByUsernameContainsAndAdminFalseOrFirstnameContainsAndAdminFalseOrLastnameContainsAndAdminFalseAllIgnoreCase(
                        param, param, param))
                .thenReturn(Arrays.asList(user1, user2));
        List<User> users = userService.findUsersByParam(param);

        assertTrue(users.contains(user1) && users.contains(user2));

        when(userRepository
                .findByUsernameContainsAndAdminFalseOrFirstnameContainsAndAdminFalseOrLastnameContainsAndAdminFalseAllIgnoreCase(
                        param, param, param))
                .thenReturn(new ArrayList<>());
        users = userService.findUsersByParam(param);

        assertTrue(users.isEmpty());
    }

    @Test
    public void findById_test() {
        User expectedUser = new User();
        when(userRepository.findById(1)).thenReturn(expectedUser);
        User actualUser = userService.findById(1);

        assertEquals(expectedUser, actualUser);
    }

    @Test(expected = DataNotFoundException.class)
    public void findById_ExceptionTest() {
        when(userRepository.findById(1)).thenReturn(null);
        userService.findById(1);
    }
}
