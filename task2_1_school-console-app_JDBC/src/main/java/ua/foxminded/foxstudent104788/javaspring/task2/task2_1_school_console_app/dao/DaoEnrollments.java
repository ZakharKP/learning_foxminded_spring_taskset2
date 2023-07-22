package ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.dao;

import java.util.List;

import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.models.Enrollment;

/**
 * The DaoEnrollments interface provides methods for interacting with
 * enrollments in a database.
 */
public interface DaoEnrollments {

	/**
	 * Adds a single enrollment to the database.
	 *
	 * @param enrollment The enrollment to be added.
	 * @return The number of rows affected (usually 1) if the enrollment was added
	 *         successfully, or -1 if an error occurred.
	 */
	int addEnrollment(Enrollment enrollment);

	/**
	 * Adds multiple enrollments to the database.
	 *
	 * @param enrollments The list of enrollments to be added.
	 * @return The number of rows affected if the enrollments were added
	 *         successfully, or -1 if an error occurred.
	 */
	int addEnrollments(List<Enrollment> enrollments);

	/**
	 * Deletes an enrollment from the database.
	 *
	 * @param enrollment The enrollment to be deleted.
	 * @return The number of rows affected (usually 1) if the enrollment was deleted
	 *         successfully, or -1 if an error occurred.
	 */
	int delete(Enrollment enrollment);

	/**
	 * Retrieves a list of enrollments by the course ID.
	 *
	 * @param courseId The ID of the course.
	 * @return A list of enrollments associated with the specified course ID.
	 */
	List<Enrollment> getByCourseId(Integer courseId);
}
