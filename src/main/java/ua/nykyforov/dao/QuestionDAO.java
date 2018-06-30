package ua.nykyforov.dao;

import ua.nykyforov.domain.Question;

import java.util.Collection;

public interface QuestionDAO {
    Collection<Question> getAllQuestions();
}
