package ua.nykyforov.service.question;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.nykyforov.dao.QuestionDAO;
import ua.nykyforov.domain.QuizQuestion;

import java.util.Collection;

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

}
