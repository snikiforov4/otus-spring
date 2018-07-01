package ua.nykyforov.service.question;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ua.nykyforov.dao.QuestionDAO;
import ua.nykyforov.domain.QuizQuestion;

import java.util.Collection;

import static com.google.common.collect.ImmutableList.toImmutableList;

@Service
public class SimpleQuestionService implements QuestionService {

    private final QuestionDAO questionDAO;
    private final int defaultNumberOfQuestions;

    @Autowired
    public SimpleQuestionService(QuestionDAO questionDAO,
                                 @Value("${quiz.defaultQuestions}") int defaultNumberOfQuestions) {
        this.questionDAO = questionDAO;
        this.defaultNumberOfQuestions = defaultNumberOfQuestions;
    }

    public Collection<QuizQuestion> getAllQuestions() {
        return questionDAO.getAllQuestions();
    }

    @Override
    public Collection<QuizQuestion> getDefaultNumberOfQuestions() {
        return getLimitNumberOfQuestions(defaultNumberOfQuestions);
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
