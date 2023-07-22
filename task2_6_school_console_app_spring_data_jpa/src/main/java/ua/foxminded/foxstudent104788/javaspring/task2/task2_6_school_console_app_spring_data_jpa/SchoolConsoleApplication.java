package ua.foxminded.foxstudent104788.javaspring.task2.task2_6_school_console_app_spring_data_jpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.log4j.Log4j2;

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
