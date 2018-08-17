package ua.nykyforov.service.library.core.dao;

import ua.nykyforov.service.library.core.domain.BookComment;

import java.util.List;

public interface BookCommentDao {

    void insert(BookComment bookComment);

    List<BookComment> getAllByBookId(int bookId);

}
