package ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.dao;

import java.util.Optional;

import ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.models.Course;

/**
 * This interface defines methods for interacting with courses in the database.
 */
public interface DaoCourses extends DaoEntity<Course> {

	/**
	 * Retrieves a Course object by its course name.
	 *
	 * @param courseName the course name
	 * @return the Course object with the given name, or null if not found
	 */
	Optional<Course> getByName(String courseName);

}
