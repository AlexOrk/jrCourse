package jr_course.service;

import jr_course.entity.User;

import java.util.List;

public interface UserService {

    public List<User> findAllExceptAdmin();
    public User findById(int id);
    public List<User> findUsersByParam(String param);

    public void deleteById(int id);
    public void deleteAllExceptAdmin();
}
