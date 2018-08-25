package ua.nykyforov.twitter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ua.nykyforov.twitter.config.WebConfig;

@SpringBootApplication(scanBasePackageClasses = WebConfig.class)
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class);
    }

}
