package ua.nykyforov.service.quiz.core.model;


import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import java.util.List;

public class QuizQuestion {
    private final String questionText;
    private final List<QuizAnswer> answers;

    public QuizQuestion(String questionText, Iterable<QuizAnswer> answers) {
        this.questionText = questionText;
        this.answers = ImmutableList.copyOf(answers);
    }

    public String getQuestionText() {
        return questionText;
    }

    public List<QuizAnswer> getAnswers() {
        return answers;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper("")
                .add("questionText", questionText)
                .add("answers", answers)
                .toString();
    }
}
