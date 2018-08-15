package ua.nykyforov.service.quiz.core.application;


import ua.nykyforov.service.quiz.core.model.QuizQuestion;

import java.util.Collection;

public interface QuestionService {
    Collection<QuizQuestion> getAllQuestions();
    Collection<QuizQuestion> getLimitNumberOfQuestions(int limit);
}
