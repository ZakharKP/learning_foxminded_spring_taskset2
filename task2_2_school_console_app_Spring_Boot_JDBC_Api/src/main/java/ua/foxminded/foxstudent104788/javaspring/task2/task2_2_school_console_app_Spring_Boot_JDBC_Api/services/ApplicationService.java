package ua.foxminded.foxstudent104788.javaspring.task2.task2_2_school_console_app_Spring_Boot_JDBC_Api.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import ua.foxminded.foxstudent104788.javaspring.task2.task2_2_school_console_app_Spring_Boot_JDBC_Api.dao.DaoCourseRosters;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_2_school_console_app_Spring_Boot_JDBC_Api.dao.DaoCourses;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_2_school_console_app_Spring_Boot_JDBC_Api.dao.DaoGroups;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_2_school_console_app_Spring_Boot_JDBC_Api.dao.DaoStudents;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_2_school_console_app_Spring_Boot_JDBC_Api.dao.impl.DaoCourseRostersJdbc;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_2_school_console_app_Spring_Boot_JDBC_Api.dao.impl.DaoCoursesJdbc;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_2_school_console_app_Spring_Boot_JDBC_Api.dao.impl.DaoGroupsJdbc;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_2_school_console_app_Spring_Boot_JDBC_Api.dao.impl.DaoStudentsJdbc;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_2_school_console_app_Spring_Boot_JDBC_Api.models.Course;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_2_school_console_app_Spring_Boot_JDBC_Api.models.CourseRoster;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_2_school_console_app_Spring_Boot_JDBC_Api.models.Group;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_2_school_console_app_Spring_Boot_JDBC_Api.models.MessageStorage;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_2_school_console_app_Spring_Boot_JDBC_Api.models.Student;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_2_school_console_app_Spring_Boot_JDBC_Api.services.impl.ConsoleUserService;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_2_school_console_app_Spring_Boot_JDBC_Api.services.impl.ResourceLoaderImpl;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_2_school_console_app_Spring_Boot_JDBC_Api.services.testdata.TestDataPlaceholder;

/**
 * 
 * This class represents the main application service. It implements the
 * Runnable interface to provide the entry point for the application logic. The
 * ApplicationService class initializes the necessary components, interacts with
 * the user, and performs various operations on the database.
 * 
 * Usage: To start the application, create an instance of ApplicationService and
 * call the run method.
 */

public class ApplicationService implements Runnable {
	private static final Logger logger = LogManager.getLogger(ApplicationService.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * Runs the application logic. It interacts with the user, initializes necessary
	 * components, and performs operations on the database.
	 */
	@Override
	public void run() {

		ResourceLoader loader = new ResourceLoaderImpl();
		MessageStorage messageStorage = loader.getMessageStorage();

		new TestDataPlaceholder(jdbcTemplate).generateData();

		try (Scanner reader = new Scanner(System.in)) {

			UserService userService = new ConsoleUserService(reader, messageStorage);
			userService.sayHello();

			userService.printMenu();

			interaction(userService);
			userService.sayByeBye();
		}

	}

	/**
	 * Handles the interaction with the user by presenting the menu, accepting user
	 * commands, and executing corresponding operations.
	 * 
	 * @param userService The UserService instance for user interaction.
	 */
	private void interaction(UserService userService) {
		userService.printAskCommand();
		Integer command = userService.getNumber();
		switch (command) {
		case 1:
			proceedGroupsByCount(userService);
			break;
		case 2:
			proceedStudentsByCourse(userService);
			break;
		case 3:
			proceedAddNewStudent(userService);
			break;
		case 4:
			proceedDelStudentById(userService);
			break;
		case 5:
			proceedStudentToCourse(userService);
			break;
		case 6:
			proceedRemoveStudentFromCourse(userService);
			break;
		case 7:
			userService.printMenu();
			break;
		case 8:
			return;
		default:
			userService.printWrongCommand();
			break;
		}

		interaction(userService);
	}

	// 1 Find all groups with less or equal students’ number
	private void proceedGroupsByCount(UserService userService) {
		DaoGroups daoGroups = new DaoGroupsJdbc(jdbcTemplate);
		DaoStudents daoStudents = new DaoStudentsJdbc(jdbcTemplate);

		Integer groupsCount = userService.getStudentsCount();
		List<Integer> groupsIds = daoStudents.getGroupSIdCount(groupsCount);

		List<Group> groups = daoGroups.get(groupsIds);
		List<String[]> answerTable = daoGroups.representAsTable(groups);

		if (!answerTable.isEmpty()) {
			userService.printTable(answerTable);
		} else {
			userService.printWrongCommand();
		}

	}

	// 2 Find all students related to the course with the given name
	private void proceedStudentsByCourse(UserService userService) {
		DaoStudents daoStudents = new DaoStudentsJdbc(jdbcTemplate);
		DaoCourses daoCourses = new DaoCoursesJdbc(jdbcTemplate);
		DaoCourseRosters daoCourseRosters = new DaoCourseRostersJdbc(jdbcTemplate);
		String courseName = userService.getCourseName().toLowerCase();
		Course course = daoCourses.getByName(courseName).get();
		Integer courseId = course.getId();

		while (courseId == null) {
			userService.printWrongCommand();
			courseName = userService.getCourseName().toLowerCase();
			course = daoCourses.getByName(courseName).get();
			courseId = course.getId();
		}

		List<Integer> studentsId = new ArrayList<>();
		List<CourseRoster> courseRosters = daoCourseRosters.getListOfRostersByCourseId(courseId);
		for (CourseRoster courseRoster : courseRosters) {
			studentsId.add(courseRoster.getStudentId());
		}

		List<Student> students = daoStudents.get(studentsId);
		List<String[]> answerTable = daoStudents.representAsTable(students);

		if (!answerTable.isEmpty()) {
			userService.printTable(answerTable);
		} else {
			userService.printWrongCommand();
		}
	}

	// 3 Add a new student
	private void proceedAddNewStudent(UserService userService) {
		DaoStudents daoStudents = new DaoStudentsJdbc(jdbcTemplate);
		String firstName = userService.getFirstName();
		String lastName = userService.getLastName();
		Integer groupId = userService.getGroupId();
		Student student = new Student(null, firstName, lastName, groupId);
		int status = daoStudents.save(student);

		if (status > 0) {
			userService.addedMessage();
		} else {
			userService.printWrongCommand();
		}

	}

	// 4 Delete a student by the STUDENT_ID
	private void proceedDelStudentById(UserService userService) {
		DaoStudents daoStudents = new DaoStudentsJdbc(jdbcTemplate);
		Integer studentId = userService.getStudentId();
		Student student = new Student(studentId, null, null, null);
		int status = daoStudents.delete(student);

		if (status > 0) {
			userService.deletedMessage();
		} else {
			userService.printWrongCommand();
		}

	}

	// 5 Add a student to the course (from a list)
	private void proceedStudentToCourse(UserService userService) {
		DaoStudents daoStudents = new DaoStudentsJdbc(jdbcTemplate);
		DaoCourses daoCourses = new DaoCoursesJdbc(jdbcTemplate);
		DaoCourseRosters daoCourseRosters = new DaoCourseRostersJdbc(jdbcTemplate);

		Integer studentId = userService.getStudentId();

		List<Course> courses = daoCourses.getAll();
		List<String[]> coursesTable = daoCourses.representAsTable(courses);

		Integer courseId = userService.askWhatCourse(coursesTable);

		int status = daoCourseRosters.save(new CourseRoster(null, studentId, courseId));

		if (status > 0) {
			userService.addedMessage();
		} else {
			userService.printWrongCommand();
		}

	}

	// 6 Remove the student from one of their courses.
	private void proceedRemoveStudentFromCourse(UserService userService) {
		DaoCourseRosters daoCourseRosters = new DaoCourseRostersJdbc(jdbcTemplate);
		Integer studentId = userService.getStudentId();
		Integer courseId = userService.getCourseId();
		CourseRoster courseRoster = new CourseRoster(null, studentId, courseId);

		int status = daoCourseRosters.delete(courseRoster);

		if (status > 0) {
			userService.removedMessage();
		} else {
			userService.printWrongCommand();
		}
	}

}
