package ua.foxminded.foxstudent104788.javaspring.task2.task2_2_school_console_app_Spring_Boot_JDBC_Api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import ua.foxminded.foxstudent104788.javaspring.task2.task2_2_school_console_app_Spring_Boot_JDBC_Api.services.ApplicationService;

/**
 * The main class that starts the School Console Spring Boot JDBC API
 * application.
 */
@SpringBootApplication
public class SchoolConsoleSpringBootJdbcApi {

	/**
	 * The main method that serves as the entry point for the application.
	 * 
	 */
	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(SchoolConsoleSpringBootJdbcApi.class, args);

		ApplicationService service = context.getBean(ApplicationService.class);
		service.run();
	}

}
