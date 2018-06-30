package ua.nykyforov.domain;

public class QuizResult {

    private final int allQuestionsSize;
    private final int correct;

    public QuizResult(int allQuestionsSize, int correct) {
        this.allQuestionsSize = allQuestionsSize;
        this.correct = correct;
    }

    public int getAllQuestionsSize() {
        return allQuestionsSize;
    }

    public int getCorrect() {
        return correct;
    }

    public float getCorrectRatio() {
        return allQuestionsSize / correct;
    }
}
