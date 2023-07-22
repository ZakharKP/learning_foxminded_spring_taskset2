package ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.dao;

import java.util.List;

import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.models.Student;

/**
 * The DaoStudents interface provides methods for interacting with students in a
 * database.
 */
public interface DaoStudents {

	/**
	 * Adds a student to the database.
	 *
	 * @param student The student to be added.
	 * @return The number of rows affected (usually 1) if the student was added
	 *         successfully, or -1 if an error occurred.
	 */
	int addStudent(Student student);

	/**
	 * Adds multiple students to the database.
	 *
	 * @param students The list of students to be added.
	 * @return The number of rows affected if the students were added successfully,
	 *         or -1 if an error occurred.
	 */
	int addStudents(List<Student> students);

	/**
	 * Retrieves students by their IDs.
	 *
	 * @param studentsId The list of student IDs.
	 * @return A list of students associated with the specified IDs.
	 */
	List<Student> getById(List<Integer> studentsId);

	/**
	 * Retrieves a table representation of the students.
	 *
	 * @param students The list of students.
	 * @return A list of string arrays representing the table, with each string
	 *         array representing a row of the table.
	 */
	List<String[]> getTable(List<Student> students);

	/**
	 * Deletes a student from the database.
	 *
	 * @param student The student to be deleted.
	 * @return The number of rows affected (usually 1) if the student was deleted
	 *         successfully, or -1 if an error occurred.
	 */
	int delete(Student student);

	/**
	 * Retrieves the group IDs based on the count of students in each group.
	 *
	 * @param studentsCount The count of students in a group.
	 * @return A list of students associated with the specified group count.
	 */
	List<Student> getGroupIdByGroupCount(Integer studentsCount);
}