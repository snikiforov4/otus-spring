package ua.nykyforov.dao;

import com.google.common.collect.ImmutableList;
import ua.nykyforov.domain.Question;

import java.util.Collection;

public class StubQuestionDAO implements QuestionDAO {
    @Override
    public Collection<Question> getAllQuestions() {
        return ImmutableList.of(
                new Question("Do you like Spring Framework?", ImmutableList.of("Yes", "No"))
        );
    }
}
