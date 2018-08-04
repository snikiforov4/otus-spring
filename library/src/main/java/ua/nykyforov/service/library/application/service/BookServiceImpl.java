package ua.nykyforov.service.library.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.nykyforov.service.library.core.application.BookService;
import ua.nykyforov.service.library.core.dao.BookDao;
import ua.nykyforov.service.library.core.domain.Book;

import java.util.Collection;

@Service
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;

    @Autowired
    public BookServiceImpl(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    public void save(Book book) {
        bookDao.insert(book);
    }

    @Override
    public Book getById(int id) {
        return bookDao.getById(id);
    }

    @Override
    public void deleteById(int id) {
        bookDao.deleteById(id);
    }

    @Override
    public Collection<Book> findByTitle(String title) {
        return bookDao.findByTitle(title);
    }
}
