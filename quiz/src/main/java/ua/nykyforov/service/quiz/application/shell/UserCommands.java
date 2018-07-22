package ua.nykyforov.service.quiz.application.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ua.nykyforov.service.quiz.core.application.UserService;
import ua.nykyforov.service.quiz.core.model.User;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

@ShellComponent
public class UserCommands {

    private final UserService userService;

    @Autowired
    public UserCommands(UserService userService) {
        this.userService = userService;
    }

    @ShellMethod("Add new user.")
    public String addUser(@NotEmpty String firstName, @NotEmpty String lastName) {
        User user = userService.save(new User(firstName, lastName));
        return "User was added with ID=" + user.getId();
    }

    @ShellMethod("Find user by id.")
    public String getUser(@Positive long id) {
        return userService.getById(id)
                .map(User::toString)
                .orElse(String.format("User with id=%s not found", id));
    }

}
