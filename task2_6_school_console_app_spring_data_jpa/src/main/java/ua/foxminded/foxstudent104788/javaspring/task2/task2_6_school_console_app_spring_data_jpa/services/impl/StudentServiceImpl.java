
package ua.foxminded.foxstudent104788.javaspring.task2.task2_6_school_console_app_spring_data_jpa.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.log4j.Log4j2;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_6_school_console_app_spring_data_jpa.models.Constants;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_6_school_console_app_spring_data_jpa.models.Course;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_6_school_console_app_spring_data_jpa.models.Student;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_6_school_console_app_spring_data_jpa.repository.StudentsRepository;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_6_school_console_app_spring_data_jpa.services.StudentService;

@Log4j2

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentsRepository studentsRepository;

	/**
	 * Retrieves a table representation of the students.
	 *
	 * @param students The list of students.
	 * @return A list of string arrays representing the table, with each string
	 *         array representing a row of the table.
	 */

	@Override
	public List<String[]> representAsTable(List<Student> students) {

		log.info("preparing table of students");

		List<String[]> table = new ArrayList<>();

		table.add(new String[] { Constants.STUDENT_COLUMN_NAME_ID, Constants.STUDENT_COLUMN_NAME_FIRST_NAME,
				Constants.STUDENT_COLUMN_NAME_LAST_NAME, Constants.GROUP_COLUMN_NAME_ID, "courses amount" });

		Integer groupId;

		for (Student student : students) {

			groupId = student.getGroup() == null ? null : student.getGroup().getId();

			table.add(new String[] { String.valueOf(student.getId()), String.valueOf(student.getFirstName()),
					String.valueOf(student.getLastName()), String.valueOf(groupId),
					String.valueOf(student.getCourses().size()) });
		}

		return table;
	}

	/**
	 * Gets an student by its ID.
	 *
	 * @param id The ID of the student.
	 * @return An Optional containing the student if found, or an empty Optional if
	 *         not found.
	 */

	@Override

	public Optional<Student> getStudent(Integer id) {
		log.info("Start searching of Student by id=" + id);
		return studentsRepository.findById(id);
	}

	/**
	 * Gets List of students by ID List.
	 *
	 * @param ID List of the student.
	 * @return List containing the students if found, or an empty List
	 */

	@Override

	public List<Student> getListOfStudentsByIdsList(List<Integer> ids) {

		log.info("Start searching of Students by id List");

		return studentsRepository.findAllById(ids);
	}

	/**
	 * Retrieves all students.
	 *
	 * @return A list of all students.
	 */

	@Override

	public List<Student> getAll() {

		log.info("Start research of All Students");
		return studentsRepository.findAll();
	}

	/**
	 * Adds an student.
	 *
	 * @param student The student to be added.
	 * @return The new entity if the student was added successfully, or null if an
	 *         error occurred.
	 */

	@Override

	public Student saveNewStudent(Student student) {
		if (student == null) {
			log.info("Can't save - student is NULL");
			return null;
		}

		log.info("Start saving new student: " + student.toString());

		if (student.getGroup() != null) {
			student.getGroup().getStudents().add(student);
		}

		for (Course course : student.getCourses()) {
			course.getStudents().add(student);
		}

		Student savedStudent = null;

		try {
			savedStudent = studentsRepository.save(student);
		} catch (IllegalArgumentException e) {
			log.error("Something went wrong trying to save course" + e.getMessage());
			return null;
		}

		log.info("Sucesfully saved " + student.toString());
		return savedStudent;
	}

	/**
	 * Adds List of students.
	 *
	 * @param List of students The students to be added.
	 * @return The List of saved Students if the student was added successfully, or
	 *         null if an error occurred.
	 */
	@Override
	public List<Student> saveAllStudents(List<Student> students) {
		if (students == null) {
			log.info("Can't save - students is NULL");
			return null;
		}

		log.info(String.format("Start saving List of %s students:", students.size()));

		List<Student> savedStudents = studentsRepository.saveAll(students);

		log.info(savedStudents.size() + " new students was saved");

		return savedStudents;

	}

	/**
	 * Deletes an student.
	 *
	 * @param student The student to be deleted.
	 */
	@Override
	@Transactional
	public void deleteStudent(Student student) {

		log.info("Start deleting student: " + student.toString());

		studentsRepository.delete(student);

	}

	@Override

	public boolean isEmpty() {

		log.info("Start counting students");

		return studentsRepository.count() == 0;
	}

	@Override

	public Student addStudentToCourse(Student student, Course course) {
		student.getCourses().add(course);
		course.getStudents().add(student);

		Student updatedStudent = null;

		try {
			updatedStudent = studentsRepository.save(student);
			log.info("Sucesfully added " + student.toString() + " to " + course.toString());
		} catch (IllegalArgumentException e) {
			log.error("Something went wrong trying to add student to course" + e.getMessage());
		}

		return updatedStudent;

	}

	@Override

	public Student remoweCourse(Student student, Course course) {
		student.getCourses().remove(course);
		course.getStudents().remove(student);

		Student updatedStudent = null;

		try {
			updatedStudent = studentsRepository.save(student);
			log.info("Sucesfully removed " + student.toString() + " from " + course.toString());
		} catch (IllegalArgumentException e) {
			log.error("Something went wrong trying remove student from course" + e.getMessage());
		}

		return updatedStudent;
	}

	@Override
	public Student updateStudent(Student student) {
		if (student == null) {
			log.info("Can't update - student is NULL");
			return null;
		}
		log.info("Starting update " + student.toString());

		Student updatedStudent = null;

		try {
			updatedStudent = studentsRepository.save(student);
			log.info("Sucesfully updated " + updatedStudent.toString());

		} catch (IllegalArgumentException e) {
			log.error("Something went wrong trying to update student" + e.getMessage());
		}

		return updatedStudent;
	}

}
