package ua.nykyforov.service.quiz.application.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ua.nykyforov.service.quiz.application.config.QuizConfig;
import ua.nykyforov.service.quiz.core.application.UserService;
import ua.nykyforov.service.quiz.core.model.User;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

@ShellComponent
public class UserCommands {

    private final UserService userService;
    private final MessageSource appMessageSource;
    private final QuizConfig quizConfig;

    @Autowired
    public UserCommands(UserService userService, MessageSource appMessageSource, QuizConfig quizConfig) {
        this.userService = userService;
        this.appMessageSource = appMessageSource;
        this.quizConfig = quizConfig;
    }

    @ShellMethod("Add new user.")
    public String addUser(@NotEmpty String firstName, @NotEmpty String lastName) {
        User user = userService.save(new User(firstName, lastName));
        return appMessageSource.getMessage("cmd.add.user.result",
                new Object[]{user.getId()}, quizConfig.getSettings().getLocale());
    }

    @ShellMethod("Find user by id.")
    public String getUser(@Positive long id) {
        return userService.getById(id)
                .map(User::toString)
                .orElseGet(() -> appMessageSource.getMessage("user.not-found",
                        new Object[]{id}, quizConfig.getSettings().getLocale()));
    }

}
