package ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.services.entityservices;

import java.util.List;
import java.util.Optional;

import ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.models.Student;

public interface StudentService {

	/**
	 * Gets an student by its ID.
	 *
	 * @param id The ID of the student.
	 * @return An Optional containing the student if found, or an empty Optional if
	 *         not found.
	 */
	Optional<Student> getStudent(Integer id);
	
	/**
	 * Gets List of students by ID List.
	 *
	 * @param ID List of the student.
	 * @return List containing the students if found, or an empty List
	 */
	List<Student> getListOfStudentsByIdsList(List<Integer> ids);

	/**
	 * Retrieves all students.
	 *
	 * @return A list of all students.
	 */
	List<Student> getAll();

	/**
	 * Adds an student.
	 *
	 * @param student The student to be added.
	 * @return The number of rows affected (usually 1) if the student was added
	 *         successfully, or -1 if an error occurred.
	 */
	int saveNewStudent(Student student);
	
	/**
	 * Adds List of students.
	 *
	 * @param List of students The students to be added.
	 * @return The number of rows affected (usually 1) if the student was added
	 *         successfully, or -1 if an error occurred.
	 */
	int saveAllStudents(List<Student> students);
	

	/**
	 * Deletes an student.
	 *
	 * @param student The student to be deleted.
	 * @return The number of rows affected (usually 1) if the student was deleted
	 *         successfully, or -1 if an error occurred.
	 */
	int deleteStudent(Student student);

	/**
	 * Retrieves a table representation of the students.
	 *
	 * @param students The list of students.
	 * @return A list of string arrays representing the table, with each string
	 *         array representing a row of the table.
	 */
	List<String[]> representAsTable(List<Student> students);
	
	
	
	boolean isAnyStudent();
	
	
	
	/**
	 * Retrieves the group IDs based on the count of students in each group.
	 *
	 * @param groupsCount The count of students in a group.
	 * @return A list of students associated with the specified group count.
	 */
	public List<Integer> getGroupsIdsByStudentsCount(Integer groupsCount);
}
