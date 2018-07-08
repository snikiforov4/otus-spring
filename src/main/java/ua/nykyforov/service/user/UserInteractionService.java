package ua.nykyforov.service.user;


import ua.nykyforov.domain.QuizResult;
import ua.nykyforov.domain.User;

import java.util.List;
import java.util.Locale;

public interface UserInteractionService {

    User askUserInfo(Locale locale);

    int askQuestion(String question, List<String> answers, Locale locale);

    void sendQuizResult(User user, QuizResult quizResult, Locale locale);
}
