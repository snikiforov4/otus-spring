package ua.nykyforov.service.quiz.core.application;


import java.util.List;
import java.util.Locale;

public interface UserInteractionService {

    int askQuestion(String question, List<String> answers, Locale locale);

}
