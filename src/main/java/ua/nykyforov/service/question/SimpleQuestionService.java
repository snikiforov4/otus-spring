package ua.nykyforov.service.question;


import ua.nykyforov.dao.QuestionDAO;
import ua.nykyforov.domain.Question;

import java.util.Collection;

public class SimpleQuestionService implements QuestionService {

    private final QuestionDAO questionDAO;

    public SimpleQuestionService(QuestionDAO questionDAO) {
        this.questionDAO = questionDAO;
    }

    public Collection<Question> getAllQuestions() {
        return questionDAO.getAllQuestions();
    }

}
