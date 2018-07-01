package ua.nykyforov;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ua.nykyforov.controller.QuizController;

@Configuration
@ComponentScan(basePackages = {"ua.nykyforov.controller", "ua.nykyforov.dao", "ua.nykyforov.service"})
public class Main {

    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(Main.class);
        QuizController quizController = ctx.getBean(QuizController.class);
        quizController.passTest();
    }

}
