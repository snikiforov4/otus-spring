package ua.nykyforov.service.library.core.application;

import ua.nykyforov.service.library.core.domain.BookComment;

import java.util.List;

public interface BookCommentService {

    void save(BookComment bookComment);

    List<BookComment> getAllByBookId(int bookId);

}
