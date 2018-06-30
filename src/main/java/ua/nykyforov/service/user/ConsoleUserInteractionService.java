package ua.nykyforov.service.user;

import ua.nykyforov.domain.QuizResult;
import ua.nykyforov.domain.User;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import static com.google.common.math.DoubleMath.roundToInt;
import static java.math.RoundingMode.UP;

public class ConsoleUserInteractionService implements UserInteractionService {

    @Override
    public User askUserInfo() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your name:");
        String name = sc.next();
        return new User(name);
    }

    @Override
    public int askQuestion(String question, List<String> answers) {
        System.out.println(question);
        printPossibleAnswers(answers);
        int answer = getAndValidateUserAnswer(answers.size());
        return answer - 1;
    }

    private void printPossibleAnswers(List<String> answers) {
        for (int i = 0; i < answers.size(); i++) {
            String answer = answers.get(i);
            System.out.println(String.format("[%d] %s", i + 1, answer));
        }
    }

    private int getAndValidateUserAnswer(int size) {
        Scanner sc = new Scanner(System.in);
        int answer = -1;
        boolean isNotValid = true;
        do {
            try {
                answer = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number");
                sc = new Scanner(System.in);
                continue;
            }
            if (answer < 1 || answer > size) {
                System.out.println("Enter number between 1 and " + size);
            } else {
                isNotValid = false;
            }
        } while (isNotValid);
        return answer;
    }

    @Override
    public void sendQuizResult(User user, QuizResult quizResult) {
        System.out.println(String.format("%nTEST RESULT"));
        System.out.println("=================================");
        System.out.println(String.format("%s", user.getName()));
        System.out.println(String.format("score: %d%%", roundToInt(quizResult.getCorrectRatio() * 100, UP)));
        System.out.println("=================================");
    }

}
