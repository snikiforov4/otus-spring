package ua.nykyforov.service.library.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.nykyforov.service.library.core.application.BookService;
import ua.nykyforov.service.library.core.dao.BookDao;
import ua.nykyforov.service.library.core.domain.Book;

import java.util.Collection;

import static com.google.common.base.Preconditions.checkArgument;

@Service
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;

    @Autowired
    public BookServiceImpl(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    public void save(Book book) {
        int inserted = bookDao.insert(book);
        checkArgument(inserted == 1, "affected number of rows: %s", inserted);
    }

    @Override
    public Book getById(int id) {
        return bookDao.getById(id);
    }

    @Override
    public void deleteById(int id) {
        int deleted = bookDao.deleteById(id);
        checkArgument(deleted == 1, "affected number of rows: %s", deleted);
    }

    @Override
    public Collection<Book> findByTitle(String title) {
        return bookDao.findByTitle(title);
    }
}
