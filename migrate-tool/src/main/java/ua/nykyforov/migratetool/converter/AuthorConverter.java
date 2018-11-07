package ua.nykyforov.migratetool.converter;

import com.google.common.base.Converter;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Component;
import ua.nykyforov.service.library.core.domain.Author;

import javax.annotation.Nonnull;

@Component
public class AuthorConverter extends Converter<Author,
        ua.nykyforov.service.library.application.domain.Author> {

    @Override
    protected ua.nykyforov.service.library.application.domain.Author doForward(@Nonnull Author author) {
        return new ua.nykyforov.service.library.application.domain.Author(
                String.valueOf(author.getId()), author.getFirstName(), author.getLastName());
    }

    @Override
    protected Author doBackward(@Nonnull ua.nykyforov.service.library.application.domain.Author author) {
        throw new NotImplementedException("No Need");
    }

}
