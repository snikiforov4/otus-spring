package ua.nykyforov.service;


import com.google.common.collect.ImmutableList;
import ua.nykyforov.domain.Question;

import java.util.Collection;

public class SimpleQuestionService implements QuestionService {

    public Collection<Question> getQuestions() {
        return ImmutableList.of();
    }

}
