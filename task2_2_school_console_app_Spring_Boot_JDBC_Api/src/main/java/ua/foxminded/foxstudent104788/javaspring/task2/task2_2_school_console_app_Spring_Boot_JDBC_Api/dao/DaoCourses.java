package ua.foxminded.foxstudent104788.javaspring.task2.task2_2_school_console_app_Spring_Boot_JDBC_Api.dao;

import java.util.List;
import java.util.Optional;

import ua.foxminded.foxstudent104788.javaspring.task2.task2_2_school_console_app_Spring_Boot_JDBC_Api.models.Course;

/**
 * This interface defines methods for interacting with courses in the database.
 */
public interface DaoCourses extends Dao<Course> {

	/**
	 * Adds a single course to the database.
	 *
	 * @param course the course to add
	 * @return the number of affected rows in the database
	 */
	int save(Course course);

	/**
	 * Retrieves a Course object by its course name.
	 *
	 * @param courseName the course name
	 * @return the Course object with the given name, or null if not found
	 */
	Optional<Course> getByName(String courseName);

	/**
	 * Retrieves a list of all courses from the database.
	 *
	 * @return the list of Course objects
	 */
	List<Course> getAll();

	/**
	 * Retrieves a table representation of the list of courses.
	 *
	 * @param courses the list of courses
	 * @return a list of String arrays representing the table data
	 */
	List<String[]> representAsTable(List<Course> courses);
}
