package ua.nykyforov.service.quiz.application.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.nykyforov.service.quiz.core.application.QuestionService;
import ua.nykyforov.service.quiz.core.dao.QuestionDao;
import ua.nykyforov.service.quiz.core.model.QuizQuestion;

import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;
import java.util.Collection;

import static com.google.common.collect.ImmutableList.toImmutableList;

@Service
@NotThreadSafe
public class CacheableQuestionService implements QuestionService {

    @Nullable
    private Collection<QuizQuestion> quizQuestionsCache;
    private final QuestionDao questionDao;

    @Autowired
    public CacheableQuestionService(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    public Collection<QuizQuestion> getAllQuestions() {
        if (quizQuestionsCache == null) {
            quizQuestionsCache = questionDao.getAllQuestions();
        }
        return quizQuestionsCache;
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
