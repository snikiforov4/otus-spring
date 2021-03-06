package ua.nykyforov.service.quiz.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ua.nykyforov.service.quiz.core.application.UserInteractionService;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

@Service
public class ConsoleUserInteractionService implements UserInteractionService {

    private final MessageSource appMessageSource;
    private final MessageSource quizMessageSource;

    @Autowired
    public ConsoleUserInteractionService(MessageSource appMessageSource,
                                         MessageSource quizMessageSource) {
        this.appMessageSource = appMessageSource;
        this.quizMessageSource = quizMessageSource;
    }

    @Override
    public int askQuestion(String question, List<String> answers, Locale locale) {
        System.out.println(quizMessageSource.getMessage(question, null, locale));
        printPossibleAnswers(answers, locale);
        int answer = getAndValidateUserAnswer(answers.size(), locale);
        return answer - 1;
    }

    private void printPossibleAnswers(List<String> answers, Locale locale) {
        for (int i = 0; i < answers.size(); i++) {
            String answer = answers.get(i);
            System.out.println(String.format("[%d] %s",
                    i + 1, quizMessageSource.getMessage(answer, null, locale)
            ));
        }
    }

    private int getAndValidateUserAnswer(int size, Locale locale) {
        Scanner sc = new Scanner(System.in);
        int answer = -1;
        boolean isNotValid = true;
        do {
            try {
                answer = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println(appMessageSource.getMessage("user.error.quiz.number.invalid", null, locale));
                sc = new Scanner(System.in);
                continue;
            }
            if (answer < 1 || answer > size) {
                String errorMsg = appMessageSource.getMessage("user.error.quiz.number.range",
                        new Object[]{size}, locale);
                System.out.println(errorMsg);
            } else {
                isNotValid = false;
            }
        } while (isNotValid);
        return answer;
    }

}
