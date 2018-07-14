package ua.nykyforov.service.quiz.application.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.nykyforov.service.quiz.core.application.QuestionService;
import ua.nykyforov.service.quiz.core.dao.QuestionDAO;
import ua.nykyforov.service.quiz.core.model.QuizQuestion;

import java.util.Collection;

import static com.google.common.collect.ImmutableList.toImmutableList;

@Service
public class SimpleQuestionService implements QuestionService {

    private final QuestionDAO questionDAO;

    @Autowired
    public SimpleQuestionService(QuestionDAO questionDAO) {
        this.questionDAO = questionDAO;
    }

    public Collection<QuizQuestion> getAllQuestions() {
        return questionDAO.getAllQuestions();
    }

    @Override
    public Collection<QuizQuestion> getLimitNumberOfQuestions(final int limit) {
        Collection<QuizQuestion> result = getAllQuestions();
        if (result.size() > limit) {
            result = result.stream().limit(limit).collect(toImmutableList());
        }
        return result;
    }

}
