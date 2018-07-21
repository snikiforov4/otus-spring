package ua.nykyforov.service.quiz.core.application;


import ua.nykyforov.service.quiz.core.model.QuizResult;
import ua.nykyforov.service.quiz.core.model.User;

import java.util.List;
import java.util.Locale;

public interface UserInteractionService {

    User askUserInfo(Locale locale);

    int askQuestion(String question, List<String> answers, Locale locale);

    void sendQuizResult(User user, QuizResult quizResult, Locale locale);
}
