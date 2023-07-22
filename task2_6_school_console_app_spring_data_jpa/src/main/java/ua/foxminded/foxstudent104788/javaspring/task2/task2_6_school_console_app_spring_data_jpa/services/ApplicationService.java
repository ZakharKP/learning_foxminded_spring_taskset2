
package ua.foxminded.foxstudent104788.javaspring.task2.task2_6_school_console_app_spring_data_jpa.services;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_6_school_console_app_spring_data_jpa.models.Course;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_6_school_console_app_spring_data_jpa.models.Group;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_6_school_console_app_spring_data_jpa.models.MessageStorage;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_6_school_console_app_spring_data_jpa.models.Student;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_6_school_console_app_spring_data_jpa.services.impl.ConsoleUserService;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_6_school_console_app_spring_data_jpa.services.impl.ResourceLoaderImpl;

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

@Log4j2
@Service
public class ApplicationService implements Runnable {

	@Autowired
	private StudentService studentService;

	@Autowired
	private GroupService groupService;

	@Autowired
	private CourseService courseService;

	@Autowired
	private TestDataPlaceholder dataPlaceholder;

	/**
	 * Runs the application logic. It interacts with the user, initializes necessary
	 * components, and performs operations on the database.
	 */

	@Override
	@PostConstruct
	public void run() {

		ResourceLoader loader = new ResourceLoaderImpl();
		MessageStorage messageStorage = loader.getMessageStorage();

		if (hasEmptyTables()) {
			log.info("start generating and pushing data into database");
			dataPlaceholder.generateData();
			log.info("Data generated and pushed succefully");
		}

		try (Scanner reader = new Scanner(System.in)) {
			log.info("Start interaction with user");

			UserService userService = new ConsoleUserService(reader, messageStorage);
			userService.sayHello();

			userService.printMenu();

			interaction(userService);
			userService.sayByeBye();

			log.debug("application worked correctrly");
			log.info("Exiting from application");
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

	// 1 Find all groups with less or equal students’ number private void
	private void proceedGroupsByCount(UserService userService) {

		log.debug("Starting case 1 - find groups by students count");

		Integer studentsCount = userService.getStudentsCount();

		List<Group> groups = groupService.findWithLessOrEqualsStudents(studentsCount);

		if (!groups.isEmpty()) {

			List<String[]> answerTable = groupService.representAsTable(groups);
			userService.printTable(answerTable);
		} else {
			userService.printWrongCommand();
		}

	}

	// 2 Find all students related to the course with the given name
	private void proceedStudentsByCourse(UserService userService) {

		log.debug("Starting case 2 - find students by course name");

		String courseName = userService.getCourseName();
		Optional<Course> courseOptional = courseService.getCourseByName(courseName);
		Course course;

		List<Student> students;
		List<String[]> answerTable;

		while (!courseOptional.isPresent()) {
			userService.printWrongCommand();
			courseName = userService.getCourseName().toLowerCase();
			courseOptional = courseService.getCourseByName(courseName);
		}

		course = courseOptional.get();

		students = course.getStudents().stream().collect(Collectors.toList());

		if (!students.isEmpty()) {

			answerTable = studentService.representAsTable(students);

			userService.printTable(answerTable);
		} else {
			userService.printWrongCommand();
		}
	}

	// 3 Add a new student
	private void proceedAddNewStudent(UserService userService) {

		log.debug("Starting case 3 - add new student");

		String firstName = userService.getFirstName();
		String lastName = userService.getLastName();
		Integer groupId = userService.getGroupId();
		Group group = null;
		Optional<Group> optionalGroup;

		if (groupId != null) {
			optionalGroup = groupService.getGroup(groupId);
			if (optionalGroup.isPresent()) {
				group = optionalGroup.get();
			}
		}

		Student student = Student.builder().firstName(firstName).lastName(lastName).build();
		Student savedStudent = studentService.saveNewStudent(student);

		if (savedStudent != null && group != null) {
			savedStudent.setGroup(group);
			savedStudent.getGroup().getStudents().add(student);
			savedStudent = studentService.updateStudent(savedStudent);
		}

		if (savedStudent != null) {
			userService.addedMessage();
		} else {
			userService.printWrongCommand();
		}

	}

	// 4 Delete a student by the STUDENT_ID
	private void proceedDelStudentById(UserService userService) {

		log.debug("Starting case 4 - delete student by id");

		Integer studentId = userService.getStudentId();

		Optional<Student> student = studentService.getStudent(studentId);

		while (!student.isPresent()) {
			userService.printWrongCommand();
			studentId = userService.getStudentId();
			student = studentService.getStudent(studentId);
		}

		studentService.deleteStudent(student.get());

		userService.deletedMessage();

	}

	// 5 Add a student to the course (from a list)
	private void proceedStudentToCourse(UserService userService) {

		log.debug("Starting case 5 - add a student to ");

		Integer studentId = userService.getStudentId();

		Optional<Student> student = studentService.getStudent(studentId);

		while (!student.isPresent()) {
			userService.printWrongCommand();
			studentId = userService.getStudentId();
			student = studentService.getStudent(studentId);
		}

		List<Course> courses = courseService.getAll();

		List<String[]> coursesTable = courseService.representAsTable(courses);

		Integer courseId = userService.askWhatCourse(coursesTable);

		Optional<Course> course = courseService.getCourse(courseId);

		while (!course.isPresent()) {
			userService.printWrongCommand();
			courseId = userService.getCourseId();
			course = courseService.getCourse(courseId);
		}

		Student updatedStudent = studentService.addStudentToCourse(student.get(), course.get());

		if (updatedStudent != null) {
			userService.addedMessage();
		} else {
			userService.printWrongCommand();
		}

	}

	// 6 Remove the student from one of their courses.
	private void proceedRemoveStudentFromCourse(UserService userService) {

		log.debug("Starting case 6 - remove a student from course ");

		Integer studentId = userService.getStudentId();

		Optional<Student> student = studentService.getStudent(studentId);

		while (!student.isPresent()) {

			userService.printWrongCommand();
			studentId = userService.getStudentId();
			student = studentService.getStudent(studentId);
		}

		Integer courseId = userService.getCourseId();

		Optional<Course> course = courseService.getCourse(courseId);

		while (!course.isPresent()) {
			userService.printWrongCommand();
			courseId = userService.getCourseId();
			course = courseService.getCourse(courseId);
		}

		Student updatedStudent = studentService.remoweCourse(student.get(), course.get());

		if (updatedStudent != null) {
			userService.removedMessage();
		} else {
			userService.printWrongCommand();
		}
	}

	private boolean hasEmptyTables() {

		log.debug("Checking for empty tables");

		return courseService.isEmpty() || groupService.isEmpty() || studentService.isEmpty();
	}

}
