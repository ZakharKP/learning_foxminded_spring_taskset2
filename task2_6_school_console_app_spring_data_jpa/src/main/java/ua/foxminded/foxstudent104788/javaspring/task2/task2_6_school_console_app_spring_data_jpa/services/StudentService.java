package ua.foxminded.foxstudent104788.javaspring.task2.task2_6_school_console_app_spring_data_jpa.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ua.foxminded.foxstudent104788.javaspring.task2.task2_6_school_console_app_spring_data_jpa.models.Course;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_6_school_console_app_spring_data_jpa.models.Student;

@Service
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

	
	Student updateStudent(Student student);
	
	/**
	 * Adds an student.
	 *
	 * @param student The student to be added.
	 * @return The new entity if the student was added
	 *         successfully, or null if an error occurred.
	 */
	Student saveNewStudent(Student student);

	/**
	 * Adds List of students.
	 *
	 * @param List of students The students to be added.
	 * @return The List of  saved Students if the student was added
	 *         successfully, or null if an error occurred.
	 */
	List<Student> saveAllStudents(List<Student> students);

	/**
	 * Deletes an student.
	 *
	 * @param student The student to be deleted.
	 */
	void deleteStudent(Student student);

	/**
	 * Retrieves a table representation of the students.
	 *
	 * @param students The list of students.
	 * @return A list of string arrays representing the table, with each string
	 *         array representing a row of the table.
	 */
	List<String[]> representAsTable(List<Student> students);

	boolean isEmpty();

	Student addStudentToCourse(Student student, Course course);

	Student remoweCourse(Student student, Course course);


}
