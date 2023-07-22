package ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.services;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.dao.DaoConnectionHolder;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.dao.DaoCourses;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.dao.DaoEnrollments;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.dao.DaoGroups;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.dao.DaoStudents;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.dao.impl.DaoConnectionHolderImpl;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.dao.impl.DaoCoursesImpl;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.dao.impl.DaoEnrollmentsIpml;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.dao.impl.DaoGroupsImpl;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.dao.impl.DaoStudentsImpl;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.models.Course;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.models.DaoConnectionSettings;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.models.Enrollment;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.models.Group;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.models.MessageStorage;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.models.Student;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.services.impl.ConsoleUserService;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.services.impl.PathStorageImpl;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.services.impl.ResourceLoaderImpl;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.services.testdata.TestDataPlaceholder;

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

	@Override
	public void run() {

		PathStorage pathStorage = new PathStorageImpl(Paths.get("src", "main", "resourses"));
		ResourceLoader loader = new ResourceLoaderImpl(pathStorage);

		DaoConnectionSettings daoConnectionSettings = loader.getDaoConnectionSettings();
		DaoConnectionHolder connection = new DaoConnectionHolderImpl(daoConnectionSettings);

		connection.executeScript(pathStorage.getCreateTableScriptPath());

		MessageStorage messageStorage = loader.getMessageStorage();

		new TestDataPlaceholder(connection).generateData();

		try (Scanner reader = new Scanner(System.in)) {

			UserService userService = new ConsoleUserService(reader, messageStorage);
			userService.sayHello();

			userService.printMenu();

			interaction(userService, connection);
			userService.sayByeBye();
		}

	}

	private void interaction(UserService userService, DaoConnectionHolder connection) {
		userService.printAskCommand();
		Integer command = userService.getNumber();
		switch (command) {
		case 1:
			proceedGroupsByCount(userService, connection);
			break;
		case 2:
			proceedStudentsByCourse(userService, connection);
			break;
		case 3:
			proceedAddNewStudent(userService, connection);
			break;
		case 4:
			proceedDelStudentById(userService, connection);
			break;
		case 5:
			proceedStudentToCourse(userService, connection);
			break;
		case 6:
			proceedRemoveStudentFromCourse(userService, connection);
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

		interaction(userService, connection);
	}

	// 1 Find all groups with less or equal students’ number
	private void proceedGroupsByCount(UserService userService, DaoConnectionHolder connection) {
		DaoGroups daoGroups = new DaoGroupsImpl(connection);
		DaoStudents daoStudents = new DaoStudentsImpl(connection);

		Integer studentsCount = userService.getStudentsCount();
		List<Student> students = daoStudents.getGroupIdByGroupCount(studentsCount);
		List<Integer> groupIds = new ArrayList<>();

		while (students.isEmpty()) {
			userService.printWrongCommand();
			studentsCount = userService.getStudentsCount();
			students = daoStudents.getGroupIdByGroupCount(studentsCount);
		}

		for (Student student : students) {
			if (!groupIds.contains(groupIds)) {
				groupIds.add(student.getGroupId());
			}
		}

		List<Group> groups = daoGroups.getById(groupIds);
		List<String[]> answerTable = daoGroups.getTable(groups);

		if (!answerTable.isEmpty()) {
			userService.printTable(answerTable);
		} else {
			userService.printWrongCommand();
		}

	}

	// 2 Find all students related to the course with the given name
	private void proceedStudentsByCourse(UserService userService, DaoConnectionHolder connection) {
		DaoStudents daoStudents = new DaoStudentsImpl(connection);
		DaoCourses daoCourses = new DaoCoursesImpl(connection);
		DaoEnrollments daoEnrollments = new DaoEnrollmentsIpml(connection);
		String courseName = userService.getCourseName().toLowerCase();
		Course course = daoCourses.getByName(courseName);
		Integer courseId = course.getId();

		while (courseId == null) {
			userService.printWrongCommand();
			courseName = userService.getCourseName().toLowerCase();
			course = daoCourses.getByName(courseName);
			courseId = course.getId();
		}

		List<Integer> studentsId = new ArrayList<>();
		List<Enrollment> enrollments = daoEnrollments.getByCourseId(courseId);
		for (Enrollment enrollment : enrollments) {
			studentsId.add(enrollment.getStudentId());
		}

		List<Student> students = daoStudents.getById(studentsId);
		List<String[]> answerTable = daoStudents.getTable(students);

		if (!answerTable.isEmpty()) {
			userService.printTable(answerTable);
		} else {
			userService.printWrongCommand();
		}
	}

	// 3 Add a new student
	private void proceedAddNewStudent(UserService userService, DaoConnectionHolder connection) {
		DaoStudents daoStudents = new DaoStudentsImpl(connection);
		String firstName = userService.getFirstName();
		String lastName = userService.getLastName();
		Integer groupId = userService.getGroupId();
		Student student = new Student(null, firstName, lastName, groupId);
		int status = daoStudents.addStudent(student);

		if (status == 1) {
			userService.addedMessage();
		} else {
			userService.printWrongCommand();
		}

	}

	// 4 Delete a student by the STUDENT_ID
	private void proceedDelStudentById(UserService userService, DaoConnectionHolder connection) {
		DaoStudents daoStudents = new DaoStudentsImpl(connection);
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
	private void proceedStudentToCourse(UserService userService, DaoConnectionHolder connection) {
		DaoStudents daoStudents = new DaoStudentsImpl(connection);
		DaoCourses daoCourses = new DaoCoursesImpl(connection);
		DaoEnrollments daoEnrollments = new DaoEnrollmentsIpml(connection);

		Integer studentId = userService.getStudentId();

		List<Course> courses = daoCourses.getCourses();
		List<String[]> coursesTable = daoCourses.getTable(courses);

		Integer courseId = userService.askWhatCourse(coursesTable);

		int status = daoEnrollments.addEnrollment(new Enrollment(null, studentId, courseId));

		if (status > 0) {
			userService.addedMessage();
		} else {
			userService.printWrongCommand();
		}

	}

	// 6 Remove the student from one of their courses.
	private void proceedRemoveStudentFromCourse(UserService userService, DaoConnectionHolder connection) {
		DaoEnrollments daoEnrollments = new DaoEnrollmentsIpml(connection);
		Integer studentId = userService.getStudentId();
		Integer courseId = userService.getCourseId();
		Enrollment enrollment = new Enrollment(null, studentId, courseId);

		int status = daoEnrollments.delete(enrollment);

		if (status > 0) {
			userService.removedMessage();
		} else {
			userService.printWrongCommand();
		}
	}

}
