package ua.nykyforov.service.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.nykyforov.service.library.application.AuthorService;
import ua.nykyforov.service.library.dao.AuthorDao;
import ua.nykyforov.service.library.domain.Author;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDao authorDao;

    @Autowired
    public AuthorServiceImpl(AuthorDao authorDao) {
        this.authorDao = authorDao;
    }

    @Override
    public void save(Author author) {
        authorDao.insert(author);
    }

    @Override
    public Author getById(int id) {
        return authorDao.getById(id);
    }

}
