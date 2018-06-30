package ua.nykyforov.dao;

import ua.nykyforov.domain.QuizQuestion;

import java.util.Collection;

import static com.google.common.collect.ImmutableList.of;
import static ua.nykyforov.domain.QuizAnswer.correct;
import static ua.nykyforov.domain.QuizAnswer.incorrect;

public class StubQuestionDAO implements QuestionDAO {
    @Override
    public Collection<QuizQuestion> getAllQuestions() {
        return of(
                new QuizQuestion("Do you like Spring Framework?", of(correct("Yes"), incorrect("No")))
        );
    }
}
