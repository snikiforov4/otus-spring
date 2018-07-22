package ua.nykyforov.service.quiz.application.shell;

import org.apache.commons.text.TextStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ua.nykyforov.service.quiz.application.config.QuizConfig;
import ua.nykyforov.service.quiz.core.application.QuizController;
import ua.nykyforov.service.quiz.core.application.UserService;
import ua.nykyforov.service.quiz.core.model.QuizResult;
import ua.nykyforov.service.quiz.core.model.User;

import javax.validation.constraints.Positive;
import java.util.Locale;

@ShellComponent
public class QuizCommands {

    private final UserService userService;
    private final QuizController quizController;
    private final MessageSource appMessageSource;
    private final QuizConfig quizConfig;

    @Autowired
    public QuizCommands(UserService userService, QuizController quizController,
                        MessageSource appMessageSource, QuizConfig quizConfig) {
        this.userService = userService;
        this.quizController = quizController;
        this.appMessageSource = appMessageSource;
        this.quizConfig = quizConfig;
    }

    @ShellMethod("Pass quiz with specified user.")
    public String passQuiz(@Positive long userId) {
        User user = getUser(userId);
        QuizResult quizResult = quizController.passTest();
        return buildTestResult(user, quizResult);
    }

    private User getUser(long id) {
        return userService.getById(id)
                .orElseThrow(() -> new IllegalArgumentException(appMessageSource.getMessage("user.not-found",
                        new Object[]{id}, quizConfig.getSettings().getLocale())));
    }

    private String buildTestResult(User user, QuizResult quizResult) {
        Locale locale = quizConfig.getSettings().getLocale();
        return new TextStringBuilder().appendNewLine()
                .appendln(appMessageSource.getMessage("user.result.test.header", null, locale))
                .appendln("=================================")
                .appendln(appMessageSource.getMessage("user.result.test.username",
                        new Object[]{user.getFullName()}, locale))
                .appendln(appMessageSource.getMessage("user.result.test.result",
                        new Object[]{quizResult.getCorrectAnswersRatio() * 100}, locale))
                .appendln("=================================")
                .build();
    }
}
