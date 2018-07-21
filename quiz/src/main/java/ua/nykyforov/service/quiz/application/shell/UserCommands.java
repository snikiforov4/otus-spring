package ua.nykyforov.service.quiz.application.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ua.nykyforov.service.quiz.core.application.UserService;
import ua.nykyforov.service.quiz.core.model.User;

@ShellComponent
public class UserCommands {

    private final UserService userService;

    @Autowired
    public UserCommands(UserService userService) {
        this.userService = userService;
    }

    @ShellMethod("Add new user.")
    public void addUser(String firstName, String lastName) {
        User user = new User(firstName, lastName);
        userService.save(user);
    }

}
