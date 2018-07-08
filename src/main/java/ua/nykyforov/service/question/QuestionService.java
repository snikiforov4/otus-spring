package ua.nykyforov.service.question;


import ua.nykyforov.domain.QuizQuestion;

import java.util.Collection;

public interface QuestionService {
    Collection<QuizQuestion> getAllQuestions();
    Collection<QuizQuestion> getLimitNumberOfQuestions(int limit);
}
