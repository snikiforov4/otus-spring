package ua.nykyforov.service.question;


import ua.nykyforov.service.quiz.domain.model.QuizQuestion;

import java.util.Collection;

public interface QuestionService {
    Collection<QuizQuestion> getAllQuestions();
    Collection<QuizQuestion> getLimitNumberOfQuestions(int limit);
}
