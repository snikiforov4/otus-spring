package ua.nykyforov.service.quiz.application.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.nykyforov.service.quiz.core.application.QuestionService;
import ua.nykyforov.service.quiz.core.dao.QuestionDao;
import ua.nykyforov.service.quiz.core.model.QuizQuestion;

import java.util.Collection;

import static com.google.common.collect.ImmutableList.toImmutableList;

@Service
public class SimpleQuestionService implements QuestionService {

    private final QuestionDao questionDao;

    @Autowired
    public SimpleQuestionService(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    public Collection<QuizQuestion> getAllQuestions() {
        return questionDao.getAllQuestions();
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
