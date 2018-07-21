package ua.nykyforov.service.quiz.application.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ua.nykyforov.service.quiz.core.model.User;

@ShellComponent
public class UserCommands {

    @ShellMethod("Add new user.")
    public void addUser(String firstName, String lastName) {
        User user = new User(firstName, lastName);

    }

}
