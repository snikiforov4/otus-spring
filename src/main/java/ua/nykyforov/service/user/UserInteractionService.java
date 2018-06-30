package ua.nykyforov.service.user;


import ua.nykyforov.domain.User;

import java.util.List;

public interface UserInteractionService {

    User askUserInfo();

    int askQuestion(String question, List<String> answers);

}
