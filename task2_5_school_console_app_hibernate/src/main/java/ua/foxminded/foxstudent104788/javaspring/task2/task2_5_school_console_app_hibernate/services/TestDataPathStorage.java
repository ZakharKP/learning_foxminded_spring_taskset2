package ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.services;

import java.nio.file.Path;
import java.nio.file.Paths;

import ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.services.impl.PathStorageImpl;

/**
 * TestDataPathStorage is a subclass of PathStorageImpl that provides paths for
 * test data files. It extends the functionality of PathStorageImpl by adding
 * methods to retrieve specific paths for test data files such as
 * courses.properties, firstNamesStudents.txt, and lastNamesStudents.txt.
 */
public class TestDataPathStorage extends PathStorageImpl {

	/**
	 * Retrieves the path for the test courses properties file.
	 *
	 * @return the path to the test courses properties file
	 */
	public Path getTestCourses() {
		return Paths.get(this.getResourseDir().toString(), "testData", "courses.properties");
	}

	/**
	 * Retrieves the path for the test first names students file.
	 *
	 * @return the path to the test first names students file
	 */
	public Path getTestFirstNames() {
		return Paths.get(this.getResourseDir().toString(), "testData", "firstNamesStudents.txt");
	}

	/**
	 * Retrieves the path for the test last names students file.
	 *
	 * @return the path to the test last names students file
	 */
	public Path getTestLastNames() {
		return Paths.get(this.getResourseDir().toString(), "testData", "lastNamesStudents.txt");
	}

}
