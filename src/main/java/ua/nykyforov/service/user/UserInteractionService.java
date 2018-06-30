package ua.nykyforov.service.user;


import ua.nykyforov.domain.User;

public interface UserInteractionService {

    User askUserInfo();

    String askQuestion(String question, Iterable<String> answers);

}
