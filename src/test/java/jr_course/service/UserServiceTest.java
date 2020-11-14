//package jr_course.service;
//
//import jr_course.dao.UserRepository;
//import jr_course.entity.User;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//
//import java.util.Arrays;
//import java.util.List;
//
//import static org.junit.Assert.assertEquals;
//import static org.mockito.Mockito.when;
//
//@RunWith(MockitoJUnitRunner.class)
//public class UserServiceTest {
//
//    @InjectMocks
//    private UserServiceImpl userService;
//
//    @Mock
//    private UserRepository userRepository;
//
//    @Test
//    public void findAllExceptAdmin_test() {
//        when(userRepository.findAllByAdminFalse()).thenReturn();
////        User user1 = new User("Bob", "Bob", "Smith", "bobmail", false);
////        User user2 = new User("Joe", "Joe", "Black", "joemail", false);
////        List<User> expectedUsers = Arrays.asList(user1, user2);
////
////        User admin = new User("Admin", "Admin", "Main", "adminmail", true);
////
////        when(userRepository.findAll()).thenReturn(Arrays.asList(admin, user1, user2));
////        when(userRepository.findById(1)).thenReturn(admin);
////
////        List<User> actualUsers = userService.findAll(1);
////
////        assertEquals(expectedUsers, actualUsers);
//    }
//
//    @Test
//    public void findUser_test() {
//        String param = "o";
//        User user1 = new User("Bob", "Bob", "Smith", "bobmail");
//        User user2 = new User("Joe", "Joe", "Black", "joemail");
//        List<User> expectedUsers = Arrays.asList(user1, user2);
//
//        when(userRepository
//                .findByUsernameContainsOrFirstnameContainsOrLastnameContainsAllIgnoreCase(param, param, param))
//                .thenReturn(Arrays.asList(user1, user2));
//
//        List<User> actualUsers = userService.findUsers(param);
//
//        assertEquals(expectedUsers, actualUsers);
//    }
//}
