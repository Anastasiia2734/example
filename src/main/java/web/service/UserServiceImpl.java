package web.service;

import org.springframework.stereotype.Service;
import web.dao.UserDao;
import web.model.User;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public User readUser(long id) {
        return userDao.readUser(id);
    }

    @Override
    public User deleteUser(long id) {
        User user = null;
        try {
            user = userDao.deleteUser(id);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public void createOrUpdateUser(User user) {
        if (0 == user.getId()) {
            createUser(user);
        } else {
            updateUser(user);
        }
    }

    private void createUser(User user) {
        userDao.createUser(user);
    }

    private void updateUser(User user) {
        userDao.updateUser(user);
    }
}

