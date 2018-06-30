package ua.nykyforov.service;


import ua.nykyforov.domain.User;

public interface UserInteractionService {

    User askUserInfo();

    String askQuestion(String question, Iterable<String> answers);

}
