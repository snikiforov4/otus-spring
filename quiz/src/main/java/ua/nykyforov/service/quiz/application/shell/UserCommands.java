package ua.nykyforov.service.quiz.application.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ua.nykyforov.service.quiz.core.application.UserService;
import ua.nykyforov.service.quiz.core.model.User;

import java.util.Optional;

@ShellComponent
public class UserCommands {

    private final UserService userService;

    @Autowired
    public UserCommands(UserService userService) {
        this.userService = userService;
    }

    @ShellMethod("Add new user.")
    public void addUser(String firstName, String lastName) {
        User user = userService.save(new User(firstName, lastName));
        System.out.println("User was added with ID=" + user.getId());
    }

    @ShellMethod("Find user by id.")
    public void getUser(long id) {
        Optional<User> user = userService.getById(id);
        if (user.isPresent()) {
            System.out.println(user.get());
        } else {
            System.out.println(String.format("User with id=%s not found", id));
        }
    }

}
