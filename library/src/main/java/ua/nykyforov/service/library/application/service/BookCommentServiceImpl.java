package ua.nykyforov.service.library.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.nykyforov.service.library.application.annotation.Jpa;
import ua.nykyforov.service.library.core.application.BookCommentService;
import ua.nykyforov.service.library.core.dao.BookCommentDao;
import ua.nykyforov.service.library.core.domain.BookComment;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class BookCommentServiceImpl implements BookCommentService {

    private final BookCommentDao bookCommentDao;

    @Autowired
    public BookCommentServiceImpl(@Jpa BookCommentDao bookCommentDao) {
        this.bookCommentDao = bookCommentDao;
    }

    @Override
    @Transactional
    public void save(BookComment bookComment) {
        bookCommentDao.insert(bookComment);
    }

    @Override
    @Transactional
    public List<BookComment> getAllByBookId(int bookId) {
        return bookCommentDao.getAllByBookId(bookId);
    }

}
