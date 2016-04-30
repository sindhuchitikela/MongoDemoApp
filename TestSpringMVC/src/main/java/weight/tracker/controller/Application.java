package weight.tracker.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
	static String DATABASE_NAME = "WeightTracker";
	

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}