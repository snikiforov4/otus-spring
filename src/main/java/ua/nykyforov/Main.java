package ua.nykyforov;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.nykyforov.controller.QuizController;

public class Main {

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-context.xml");
        QuizController quizController = ctx.getBean(QuizController.class);
        quizController.passTest();
    }

}
