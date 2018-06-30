package ua.nykyforov.dao;

import ua.nykyforov.domain.QuizQuestion;

import java.util.Collection;

public interface QuestionDAO {
    Collection<QuizQuestion> getAllQuestions();
}
