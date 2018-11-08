package ua.nykyforov.migratetool.converter;

import com.google.common.base.Converter;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Component;
import ua.nykyforov.migratetool.domain.PostgresAuthor;
import ua.nykyforov.service.library.application.domain.Author;

import javax.annotation.Nonnull;

@Component
public class AuthorConverter extends Converter<PostgresAuthor, Author> {

    @Override
    protected Author doForward(@Nonnull PostgresAuthor author) {
        return new Author(String.valueOf(author.getId()), author.getFirstName(), author.getLastName());
    }

    @Override
    protected PostgresAuthor doBackward(@Nonnull Author author) {
        throw new NotImplementedException("No Need");
    }

}
