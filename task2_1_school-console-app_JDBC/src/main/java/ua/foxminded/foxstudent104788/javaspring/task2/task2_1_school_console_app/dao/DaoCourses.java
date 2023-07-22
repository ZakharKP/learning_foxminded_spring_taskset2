package ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.dao;

import java.util.List;

import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.models.Course;

/**
 * This interface defines methods for interacting with courses in the database.
 */
public interface DaoCourses {

	/**
	 * Adds a single course to the database.
	 *
	 * @param course the course to add
	 * @return the number of affected rows in the database
	 */
	int addCourse(Course course);

	/**
	 * Adds a list of courses to the database.
	 *
	 * @param courses the list of courses to add
	 * @return the number of affected rows in the database
	 */
	int addCourses(List<Course> courses);

	/**
	 * Retrieves a Course object by its course name.
	 *
	 * @param courseName the course name
	 * @return the Course object with the given name, or null if not found
	 */
	Course getByName(String courseName);

	/**
	 * Retrieves a list of all courses from the database.
	 *
	 * @return the list of Course objects
	 */
	List<Course> getCourses();

	/**
	 * Retrieves a table representation of the list of courses.
	 *
	 * @param courses the list of courses
	 * @return a list of String arrays representing the table data
	 */
	List<String[]> getTable(List<Course> courses);
}
