package ua.nykyforov.service.question;


import ua.nykyforov.dao.QuestionDAO;
import ua.nykyforov.domain.QuizQuestion;

import java.util.Collection;

public class SimpleQuestionService implements QuestionService {

    private final QuestionDAO questionDAO;

    public SimpleQuestionService(QuestionDAO questionDAO) {
        this.questionDAO = questionDAO;
    }

    public Collection<QuizQuestion> getAllQuestions() {
        return questionDAO.getAllQuestions();
    }

}
