package ua.nykyforov.service.question;


import ua.nykyforov.domain.Question;

import java.util.Collection;

public interface QuestionService {
    Collection<Question> getAllQuestions();
}
