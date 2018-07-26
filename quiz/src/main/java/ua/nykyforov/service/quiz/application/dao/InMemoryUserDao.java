package ua.nykyforov.service.quiz.application.dao;

import org.springframework.stereotype.Service;
import ua.nykyforov.service.quiz.core.dao.UserDao;
import ua.nykyforov.service.quiz.core.model.User;

import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;
import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;

@Service
@NotThreadSafe
public class InMemoryUserDao implements UserDao {

    private Map<Long, User> users;
    private long idGenerator;

    public InMemoryUserDao() {
        users = newHashMap();
        idGenerator = 0;
    }

    @Override
    public User save(User user) {
        user.setId(++idGenerator);
        users.put(user.getId(), user);
        return user;
    }

    @Nullable
    @Override
    public User getById(long id) {
        return users.get(id);
    }
}
