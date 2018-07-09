package ua.nykyforov.service.quiz.core.business;

import ua.nykyforov.service.quiz.core.model.QuizQuestion;

import java.util.Collection;

public interface QuestionDAO {
    Collection<QuizQuestion> getAllQuestions();
}
