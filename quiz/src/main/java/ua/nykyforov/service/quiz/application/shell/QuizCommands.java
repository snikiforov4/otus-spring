package ua.nykyforov.service.quiz.application.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ua.nykyforov.service.quiz.application.config.QuizConfig;
import ua.nykyforov.service.quiz.core.application.QuizController;
import ua.nykyforov.service.quiz.core.application.UserService;
import ua.nykyforov.service.quiz.core.model.User;

import javax.validation.constraints.Positive;

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
    public void passQuiz(@Positive long userId) {
        User user = getUser(userId);
        quizController.passTest(user);
    }

    private User getUser(@Positive long id) {
        return userService.getById(id)
                .orElseThrow(() -> new IllegalArgumentException(appMessageSource.getMessage("user.not-found",
                        new Object[]{id}, quizConfig.getSettings().getLocale())));
    }
}
