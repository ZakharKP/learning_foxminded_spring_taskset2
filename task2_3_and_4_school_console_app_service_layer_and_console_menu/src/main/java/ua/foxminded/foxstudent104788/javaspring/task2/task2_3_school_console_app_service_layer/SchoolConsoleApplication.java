package ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import lombok.extern.log4j.Log4j2;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.services.ApplicationService;

/**
 * The main class that starts the School Console Spring Boot JDBC API
 * application.
 */
@Log4j2
@SpringBootApplication
public class SchoolConsoleApplication {

	/**
	 * The main method that serves as the entry point for the application.
	 * 
	 */
	public static void main(String[] args) {
		log.info("Starting of application");
		SpringApplication.run(SchoolConsoleApplication.class, args);
		log.info("Application stopped");
		}

}
