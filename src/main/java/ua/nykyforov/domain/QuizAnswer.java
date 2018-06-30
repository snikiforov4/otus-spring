package ua.nykyforov.domain;

import com.google.common.base.MoreObjects;

public class QuizAnswer {
    private final String text;
    private final boolean isCorrect;

    public QuizAnswer(String text, boolean isCorrect) {
        this.text = text;
        this.isCorrect = isCorrect;
    }

    public static QuizAnswer correct(String text) {
        return new QuizAnswer(text, true);
    }

    public static QuizAnswer incorrect(String text) {
        return new QuizAnswer(text, false);
    }

    public String getText() {
        return text;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("text", text)
                .add("isCorrect", isCorrect)
                .toString();
    }
}
