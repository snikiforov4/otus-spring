package ua.nykyforov.domain;


import com.google.common.base.MoreObjects;

import java.util.List;

public class Question {
    private final String question;
    private final List<String> answers;

    public Question(String question, List<String> answers) {
        this.question = question;
        this.answers = answers;
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getAnswers() {
        return answers;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper("")
                .add("question", question)
                .add("answers", answers)
                .toString();
    }
}
