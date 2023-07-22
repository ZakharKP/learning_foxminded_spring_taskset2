package ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.models.Course;

@Service
public interface CourseService {

	/**
	 * Retrieves a Course object by its course name.
	 *
	 * @param courseName the course name
	 * @return the Course object with the given name, or null if not found
	 */
	public Optional<Course> getCourseByName(String courseName);

	/**
	 * Gets an course by its ID.
	 *
	 * @param id The ID of the course.
	 * @return An Optional containing the course if found, or an empty Optional if
	 *         not found.
	 */
	Optional<Course> getCourse(Integer id);

	/**
	 * Gets List of courses by ID List.
	 *
	 * @param ID List of the course.
	 * @return List containing the courses if found, or an empty List
	 */
	List<Course> getListOfCoursesByIdsList(List<Integer> ids);

	/**
	 * Retrieves all courses.
	 *
	 * @return A list of all courses.
	 */
	List<Course> getAll();

	/**
	 * Adds an course.
	 *
	 * @param course The course to be added.
	 * @return The number of rows affected (usually 1) if the course was added
	 *         successfully, or -1 if an error occurred.
	 */
	int saveCourse(Course course);

	/**
	 * Adds List of courses.
	 *
	 * @param List of courses The courses to be added.
	 * @return The number of rows affected (usually 1) if the course was added
	 *         successfully, or -1 if an error occurred.
	 */
	int saveAllCourses(List<Course> courses);

	/**
	 * Deletes an course.
	 *
	 * @param course The course to be deleted.
	 * @return The number of rows affected (usually 1) if the course was deleted
	 *         successfully, or -1 if an error occurred.
	 */
	int deleteCourse(Course course);

	/**
	 * Retrieves a table representation of the courses.
	 *
	 * @param courses The list of courses.
	 * @return A list of string arrays representing the table, with each string
	 *         array representing a row of the table.
	 */
	List<String[]> representAsTable(List<Course> courses);

	boolean isEmpty();

}
