package ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.services.ApplicationService;

/**
 * Class for start The application
 */
public class SchoolConsoleApp {

	private static final Logger logger = LogManager.getLogger(SchoolConsoleApp.class);

	public static void main(String[] args) {

		logger.info("Application Starting");
		new ApplicationService().run();
		logger.info("Application finished sucessfully");

	}
}
