package weight.tracker.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WeightTrackerApplication {
	static String DATABASE_NAME = "WeightTracker";

	public static void main(String[] args) {
		SpringApplication.run(WeightTrackerApplication.class, args);
	}
}