package ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.services.entityservices;

import java.util.List;
import java.util.Optional;

import ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.models.CourseRoster;

public interface CourseRosterService {

	/**
	 * Retrieves a list of enrollments by course ID.
	 *
	 * @param courseId the ID of the course
	 * @return the list of enrollments for the given course ID
	 */
	public List<CourseRoster> getListOfRostersByCourseId(Integer courseId);

	/**
	 * Gets an course roster by its ID.
	 *
	 * @param id The ID of the course roster.
	 * @return An Optional containing the course roster if found, or an empty
	 *         Optional if not found.
	 */
	Optional<CourseRoster> getCourseRoster(Integer id);

	/**
	 * Gets List of course rosters by ID List.
	 *
	 * @param ID List of the course roster.
	 * @return List containing the course rosters if found, or an empty List
	 */
	List<CourseRoster> getListOfCourseRostersByIdsList(List<Integer> ids);

	/**
	 * Retrieves all course rosters.
	 *
	 * @return A list of all course rosters.
	 */
	List<CourseRoster> getAll();

	/**
	 * Adds an course roster.
	 *
	 * @param course roster The course roster to be added.
	 * @return The number of rows affected (usually 1) if the course roster was
	 *         added successfully, or -1 if an error occurred.
	 */
	int saveCourseRoster(CourseRoster courseRoster);

	/**
	 * Adds List of course rosters.
	 *
	 * @param List of course rosters The course rosters to be added.
	 * @return The number of rows affected (usually 1) if the course roster was
	 *         added successfully, or -1 if an error occurred.
	 */
	int saveAll(List<CourseRoster> courseRosters);

	/**
	 * Deletes an course roster.
	 *
	 * @param course roster The course roster to be deleted.
	 * @return The number of rows affected (usually 1) if the course roster was
	 *         deleted successfully, or -1 if an error occurred.
	 */
	int deleteCourseRoster(CourseRoster courseRoster);

	/**
	 * Retrieves a table representation of the course rosters.
	 *
	 * @param course rosters The list of course rosters.
	 * @return A list of string arrays representing the table, with each string
	 *         array representing a row of the table.
	 */
	List<String[]> representAsTable(List<CourseRoster> courseRosters);

	boolean isAnyCourseRosters();

}
