package ua.nykyforov.service;


import ua.nykyforov.domain.Question;

import java.util.Collection;

public interface QuestionService {
    Collection<Question> getQuestions();
}
