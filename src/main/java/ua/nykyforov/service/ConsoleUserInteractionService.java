package ua.nykyforov.service;

import ua.nykyforov.domain.User;

import java.util.Scanner;

public class ConsoleUserInteractionService implements UserInteractionService {

    @Override
    public User askUserInfo() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your first name:");
        String firstName = sc.next();
        System.out.println("Enter your last name:");
        String lastName = sc.next();
        return new User(firstName, lastName);
    }

}
