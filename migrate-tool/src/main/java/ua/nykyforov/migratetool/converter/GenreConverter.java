package ua.nykyforov.migratetool.converter;

import com.google.common.base.Converter;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Component;
import ua.nykyforov.migratetool.domain.PostgresGenre;
import ua.nykyforov.service.library.application.domain.Genre;

import javax.annotation.Nonnull;

@Component
public class GenreConverter extends Converter<PostgresGenre, Genre> {

    @Override
    protected Genre doForward(@Nonnull PostgresGenre genre) {
        return new Genre(String.valueOf(genre.getId()), genre.getName());
    }

    @Override
    protected PostgresGenre doBackward(@Nonnull Genre genre) {
        throw new NotImplementedException("No Need");
    }

}
