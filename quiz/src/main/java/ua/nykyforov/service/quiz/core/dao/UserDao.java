package ua.nykyforov.service.quiz.core.dao;

import ua.nykyforov.service.quiz.core.model.User;

public interface UserDao {

    User save(User user);

    User getById(long id);

}
