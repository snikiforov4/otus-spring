package ua.nykyforov.service.quiz.core.dao;

import ua.nykyforov.service.quiz.core.model.QuizQuestion;

import java.util.Collection;

public interface QuestionDAO {
    Collection<QuizQuestion> getAllQuestions();
}
