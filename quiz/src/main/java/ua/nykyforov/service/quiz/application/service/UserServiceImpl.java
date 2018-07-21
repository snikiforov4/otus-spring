package ua.nykyforov.service.quiz.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.nykyforov.service.quiz.core.application.UserService;
import ua.nykyforov.service.quiz.core.dao.UserDao;
import ua.nykyforov.service.quiz.core.model.User;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User save(User user) {
        return userDao.save(user);
    }

    @Override
    public Optional<User> getById(long id) {
        return Optional.ofNullable(userDao.getById(id));
    }

}
