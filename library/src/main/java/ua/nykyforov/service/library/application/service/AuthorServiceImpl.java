package ua.nykyforov.service.library.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.nykyforov.service.library.core.application.AuthorService;
import ua.nykyforov.service.library.core.dao.AuthorDao;
import ua.nykyforov.service.library.core.domain.Author;

import static com.google.common.base.Preconditions.checkArgument;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDao authorDao;

    @Autowired
    public AuthorServiceImpl(AuthorDao authorDao) {
        this.authorDao = authorDao;
    }

    @Override
    public void save(Author author) {
        int inserted = authorDao.insert(author);
        checkArgument(inserted == 1, "affected number of rows: %s", inserted);
    }

    @Override
    public Author getById(int id) {
        return authorDao.getById(id);
    }

}
