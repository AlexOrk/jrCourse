package jr_course.service;

import jr_course.entity.User;

import java.util.List;

public interface UserService {

    public List<User> findAll(int id);
    public User findById(int id);
    public List<User> findUser(String name);

    public void deleteById(int id);
    public void deleteAllExceptAdmin(int id);
}
