package ua.nykyforov.migratetool.converter;

import com.google.common.base.Converter;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Component;
import ua.nykyforov.service.library.core.domain.Genre;

import javax.annotation.Nonnull;

@Component
public class GenreConverter extends Converter<Genre,
        ua.nykyforov.service.library.application.domain.Genre> {

    @Override
    protected ua.nykyforov.service.library.application.domain.Genre doForward(@Nonnull Genre genre) {
        return new ua.nykyforov.service.library.application.domain.Genre(
                String.valueOf(genre.getId()), genre.getName());
    }

    @Override
    protected Genre doBackward(@Nonnull ua.nykyforov.service.library.application.domain.Genre genre) {
        throw new NotImplementedException("No Need");
    }

}
