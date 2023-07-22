
package ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.services;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.log4j.Log4j2;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.models.Course;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.models.Group;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.models.Student;

/**
 * The TestDataPlaceholder class is responsible for filling in tables with test
 * data.
 */

@Service
@Log4j2
public class TestDataPlaceholder {

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private StudentService studentService;

	@Autowired
	private GroupService groupService;

	@Autowired
	private CourseService courseService;

	private TestDataGenerator dataGenerator = new TestDataGenerator(new TestDataPathStorage());

	/**
	 * Generates test data by refreshing tables and filling them with data.
	 */
	@Transactional
	public void generateData() {

		refreshTables();

		log.info(" Start generating data");
		List<Group> groups = dataGenerator.getGroups();
		List<Course> courses = dataGenerator.getCourses();
		List<Student> students = dataGenerator.getStudents();

		log.info("data was generated, start updating of database");
		fillInDataBase(groups, courses, students);
		log.info("database was update with new test data sucessfully");
	}

	/**
	 * Refreshes the tables by deleting all existing data and resetting the
	 * sequences.
	 */

	private void refreshTables() {

		log.info("Starting cleaning of tables ");

		entityManager.createQuery("DELETE FROM Course").executeUpdate();
		entityManager.createQuery("DELETE FROM Group").executeUpdate();
		entityManager.createQuery("DELETE FROM Student").executeUpdate();

		entityManager.createNativeQuery("ALTER SEQUENCE courses_course_id_seq RESTART WITH 1").executeUpdate();
		entityManager.createNativeQuery("ALTER SEQUENCE students_student_id_seq RESTART WITH 1").executeUpdate();
		entityManager.createNativeQuery("ALTER SEQUENCE groups_group_id_seq RESTART WITH 1").executeUpdate();

		log.info("database was cleaned");
	}

	/**
	 * Fills in the database tables with the provided data.
	 *
	 * @param groups   The list of groups to be inserted into the groups table.
	 * @param courses  The list of courses to be inserted into the courses table.
	 * @param students The list of students to be inserted into the students table.
	 */
	private void fillInDataBase(List<Group> groups, List<Course> courses, List<Student> students) {
		// 1
		fillInCoursesTable(courses);
		// 2
		fillInGroupTable(groups);
		// 3
		fillInStudentTable(students);

	}

	private void fillInCoursesTable(List<Course> courses) {

		log.info("Starting fill in courses table");
		courseService.saveAllCourses(courses);

	}

	private void fillInGroupTable(List<Group> groups) {

		log.info("Starting fill in groups table");
		groupService.saveAllGroups(groups);

	}

	private void fillInStudentTable(List<Student> students) {
		
		studentService.saveAllStudents(students);

		log.info("fill in students with groups");
		dataGenerator.addGroupsToStudents(students, groupService.getAll());

		log.info("fill in students with courses");
		dataGenerator.addCoursesToStudents(students, courseService.getAll());

		log.info("Starting fill in students table");
		for(Student student : students ) {
			studentService.updateStudent(student);
		}
		

	}

}
