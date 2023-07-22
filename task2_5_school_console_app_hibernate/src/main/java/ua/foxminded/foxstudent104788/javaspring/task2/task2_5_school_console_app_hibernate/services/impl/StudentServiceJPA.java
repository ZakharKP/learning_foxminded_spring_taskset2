
package ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.dao.DaoStudents;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.models.Constants;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.models.Course;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.models.Student;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.services.StudentService;

@Log4j2

@Service
public class StudentServiceJPA implements StudentService {

	@Autowired
	private DaoStudents daoStudents;

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
		return daoStudents.get(id);
	}

	/**
	 * Gets List of students by ID List.
	 *
	 * @param ID List of the student.
	 * @return List containing the students if found, or an empty List
	 */

	@Override

	public List<Student> getListOfStudentsByIdsList(List<Integer> ids) {
		List<Student> students = new ArrayList<>();

		log.info("Start searching of Students by id List");
		for (Integer id : ids) {
			daoStudents.get(id).ifPresent(students::add);
		}

		return students;
	}

	/**
	 * Retrieves all students.
	 *
	 * @return A list of all students.
	 */

	@Override

	public List<Student> getAll() {

		log.info("Start research of All Students");
		return daoStudents.getAll();
	}

	/**
	 * Adds an student.
	 *
	 * @param student The student to be added.
	 * @return The number of rows affected (usually 1) if the student was added
	 *         successfully, or -1 if an error occurred.
	 */

	@Override

	public int saveNewStudent(Student student) {

		log.info("Start saving new student: " + student.toString());

		if (student.getGroup() != null) {
			student.getGroup().getStudents().add(student);
		}

		for (Course course : student.getCourses()) {
			course.getStudents().add(student);
		}

		return daoStudents.save(student);
	}

	/**
	 * Adds List of students.
	 *
	 * @param List of students The students to be added.
	 * @return The number of rows affected (usually 1) if the student was added
	 *         successfully, or -1 if an error occurred.
	 */

	@Override

	public int saveAllStudents(List<Student> students) {

		log.info(String.format("Start saving List of %s students:", students.size()));

		int saved = 0;
		for (Student student : students) {
			
			
			int status = student.getId() == null?  daoStudents.save(student) : daoStudents.update(student);
			if (status > 0) {
				saved++;
			}
		}

		log.info(saved + " students was saved");
		return saved;
	}

	/**
	 * Deletes an student.
	 *
	 * @param student The student to be deleted.
	 * @return The number of rows affected (usually 1) if the student was deleted
	 *         successfully, or -1 if an error occurred.
	 */
	@Override

	public int deleteStudent(Student student) {

		log.info("Start deleting student: " + student.toString());

		if (student.getGroup() != null) {
			student.getGroup().getStudents().remove(student);
		}
		for (Course course : student.getCourses()) {
			course.getStudents().remove(student);
		}

		return daoStudents.delete(student);
	}

	@Override

	public boolean isEmpty() {

		log.info("Start counting students");

		return daoStudents.size() == 0;
	}

	@Override

	public int addStudentToCourse(Student student, Course course) {
		student.getCourses().add(course);
		course.getStudents().add(student);

		return daoStudents.update(student);

	}

	@Override

	public int remoweCourse(Student student, Course course) {
		student.getCourses().remove(course);
		course.getStudents().remove(student);

		return daoStudents.update(student);
	}

	@Override
	public int updateStudent(Student student) {
		
		return daoStudents.update(student);
	}

}
