package ua.foxminded.foxstudent104788.javaspring.task2.task2_2_school_console_app_Spring_Boot_JDBC_Api.dao;

import java.util.List;

import ua.foxminded.foxstudent104788.javaspring.task2.task2_2_school_console_app_Spring_Boot_JDBC_Api.models.CourseRoster;

/**
 * The DaoCourseRosters interface provides methods for interacting with
 * Course Rosters in a database.
 */
public interface DaoCourseRosters extends Dao<CourseRoster>{

	/**
	 * Adds a single Course Roster to the database.
	 *
	 * @param courseRoster The Course Roster to be added.
	 * @return The number of rows affected (usually 1) if the Course Roster was added
	 *         successfully, or -1 if an error occurred.
	 */
	int save(CourseRoster courseRoster);

	/**
	 * Deletes an Course Roster from the database.
	 *
	 * @param courseRoster The Course Roster to be deleted.
	 * @return The number of rows affected (usually 1) if the Course Roster was deleted
	 *         successfully, or -1 if an error occurred.
	 */
	int delete(CourseRoster courseRoster);

	/**
	 * Retrieves a list of enrollments by course ID.
	 *
	 * @param courseId the ID of the course
	 * @return the list of enrollments for the given course ID
	 */
	List<CourseRoster> getListOfRostersByCourseId(Integer courseId);
}
