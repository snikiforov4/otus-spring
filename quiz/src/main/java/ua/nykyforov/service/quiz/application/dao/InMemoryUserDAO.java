package ua.nykyforov.service.quiz.application.dao;

import org.springframework.stereotype.Service;
import ua.nykyforov.service.quiz.core.dao.UserDAO;
import ua.nykyforov.service.quiz.core.model.User;

import javax.annotation.concurrent.NotThreadSafe;
import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;

@Service
@NotThreadSafe
public class InMemoryUserDAO implements UserDAO {

    private Map<Long, User> users;
    private long idGenerator;

    public InMemoryUserDAO() {
        users = newHashMap();
        idGenerator = 0;
    }

    @Override
    public User save(User user) {
        user.setId(++idGenerator);
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User getById(long id) {
        return null; // todo
    }
}
