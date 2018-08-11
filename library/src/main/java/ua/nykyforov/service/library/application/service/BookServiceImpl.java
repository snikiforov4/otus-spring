package ua.nykyforov.service.library.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.nykyforov.service.library.core.application.BookService;
import ua.nykyforov.service.library.core.dao.BookDao;
import ua.nykyforov.service.library.core.domain.Book;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;

    @Autowired
    public BookServiceImpl(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    @Transactional
    public void save(Book book) {
        bookDao.insert(book);
    }

    @Override
    @Transactional
    public Optional<Book> getById(int id) {
        return Optional.ofNullable(bookDao.getById(id));
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        bookDao.deleteById(id);
    }

    @Override
    @Transactional
    public Collection<Book> findByTitleLike(String title) {
        return bookDao.findByTitleLike(title);
    }
}
