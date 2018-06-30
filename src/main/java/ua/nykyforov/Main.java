package ua.nykyforov;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.nykyforov.domain.Question;
import ua.nykyforov.domain.User;
import ua.nykyforov.service.ConsoleUserInteractionService;
import ua.nykyforov.service.SimpleQuestionService;
import ua.nykyforov.service.UserInteractionService;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-context.xml");
        UserInteractionService userInteractionService = ctx.getBean(ConsoleUserInteractionService.class);
        User user = userInteractionService.askUserInfo();
        logger.info("{}", user.getFullName());
        SimpleQuestionService questionService = ctx.getBean(SimpleQuestionService.class);
        for (Question question : questionService.getAllQuestions()) {
            String answer = userInteractionService.askQuestion(question.getQuestion(), question.getAnswers());
            logger.info("answer={}", answer);
        }
    }

}
