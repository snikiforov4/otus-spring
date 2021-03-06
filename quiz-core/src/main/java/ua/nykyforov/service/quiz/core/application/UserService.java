package ua.nykyforov.service.quiz.core.application;

import ua.nykyforov.service.quiz.core.model.User;

import java.util.Optional;

public interface UserService {

    User save(User user);

    Optional<User> getById(long id);

}
