package ua.nykyforov.dao;

import ua.nykyforov.service.quiz.domain.model.QuizQuestion;

import java.util.Collection;

public interface QuestionDAO {
    Collection<QuizQuestion> getAllQuestions();
}
