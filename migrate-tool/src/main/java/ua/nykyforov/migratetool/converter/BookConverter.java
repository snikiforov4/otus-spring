package ua.nykyforov.migratetool.converter;

import com.google.common.base.Converter;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.nykyforov.migratetool.domain.PostgresBook;
import ua.nykyforov.service.library.application.domain.Author;
import ua.nykyforov.service.library.application.domain.Book;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookConverter extends Converter<PostgresBook, Book> {

    private final AuthorConverter authorConverter;
    private final GenreConverter genreConverter;

    @Autowired
    public BookConverter(AuthorConverter authorConverter, GenreConverter genreConverter) {
        this.authorConverter = authorConverter;
        this.genreConverter = genreConverter;
    }

    @Override
    protected Book doForward(@Nonnull PostgresBook book) {
        List<Author> authors = book.getAuthors().stream().map(authorConverter::convert).collect(Collectors.toList());
        return new Book(String.valueOf(book.getId()), book.getTitle(),
                book.getGenre().map(genreConverter::convert).orElse(null), authors);
    }

    @Override
    protected PostgresBook doBackward(@Nonnull Book book) {
        throw new NotImplementedException("No Need");
    }

}
