package ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.dao.DaoCourseRosters;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.dao.DaoCourses;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.dao.DaoGroups;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.dao.DaoStudents;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.dao.impl.DaoCourseRostersJdbc;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.dao.impl.DaoCoursesJdbc;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.dao.impl.DaoGroupsJdbc;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.dao.impl.DaoStudentsJdbc;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.models.Course;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.models.CourseRoster;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.models.Group;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.models.MessageStorage;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.models.Student;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.services.entityservices.CourseRosterService;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.services.entityservices.CourseService;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.services.entityservices.GroupService;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.services.entityservices.StudentService;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.services.impl.ConsoleUserService;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.services.impl.ResourceLoaderImpl;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.services.testdata.TestDataPlaceholder;

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
	private CourseRosterService rosterService;
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

	// 1 Find all groups with less or equal students’ number
	private void proceedGroupsByCount(UserService userService) {

		log.debug("Starting case 1 - find groups by students count");
		
		Integer studentsCount = userService.getStudentsCount();
		List<Integer> groupsIds = studentService.getGroupsIdsByStudentsCount(studentsCount);

		List<Group> groups = groupService.getListOfGroupByIdsList(groupsIds);
		List<String[]> answerTable = groupService.representAsTable(groups);

		if (!answerTable.isEmpty()) {
			userService.printTable(answerTable);
		} else {
			userService.printWrongCommand();
		}

	}

	// 2 Find all students related to the course with the given name
	private void proceedStudentsByCourse(UserService userService) {
		
		log.debug("Starting case 2 - find students by course name");
		
		String courseName = userService.getCourseName().toLowerCase();
		Optional<Course> courseOptional = courseService.getCourseByName(courseName);
		Integer courseId;

		while (!courseOptional.isPresent()) {
			userService.printWrongCommand();
			courseName = userService.getCourseName().toLowerCase();
			courseOptional = courseService.getCourseByName(courseName);
		}
		
		
		courseId = courseOptional.get().getId();

		List<Integer> studentsId = new ArrayList<>();
		List<CourseRoster> courseRosters = rosterService.getListOfRostersByCourseId(courseId);
		for (CourseRoster courseRoster : courseRosters) {
			studentsId.add(courseRoster.getStudentId());
		}

		List<Student> students = studentService.getListOfStudentsByIdsList(studentsId);
		List<String[]> answerTable = studentService.representAsTable(students);

		if (!answerTable.isEmpty()) {
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
		Student student = new Student(null, firstName, lastName, groupId);
		int status = studentService.saveNewStudent(student);

		if (status > 0) {
			userService.addedMessage();
		} else {
			userService.printWrongCommand();
		}

	}

	// 4 Delete a student by the STUDENT_ID
	private void proceedDelStudentById(UserService userService) {
		
		log.debug("Starting case 4 - delete student by id");
		
		Integer studentId = userService.getStudentId();
		Student student = new Student(studentId, null, null, null);
		int status = studentService.deleteStudent(student);

		if (status > 0) {
			userService.deletedMessage();
		} else {
			userService.printWrongCommand();
		}

	}

	// 5 Add a student to the course (from a list)
	private void proceedStudentToCourse(UserService userService) {
		
		log.debug("Starting case 5 - add a student to ");
		
		Integer studentId = userService.getStudentId();

		List<Course> courses = courseService.getAll();
		List<String[]> coursesTable = courseService.representAsTable(courses);

		Integer courseId = userService.askWhatCourse(coursesTable);

		int status = rosterService.saveCourseRoster(new CourseRoster(null, studentId, courseId));

		if (status > 0) {
			userService.addedMessage();
		} else {
			userService.printWrongCommand();
		}

	}

	// 6 Remove the student from one of their courses.
	private void proceedRemoveStudentFromCourse(UserService userService) {
		
		log.debug("Starting case 6 - remove a student from course ");
		
		Integer studentId = userService.getStudentId();
		Integer courseId = userService.getCourseId();
		CourseRoster courseRoster = new CourseRoster(null, studentId, courseId);

		int status = rosterService.deleteCourseRoster(courseRoster);

		if (status > 0) {
			userService.removedMessage();
		} else {
			userService.printWrongCommand();
		}
	}

	private boolean hasEmptyTables() {

		log.debug("Checking for empty tables");
		
		return courseService.isAnyCourses() || groupService.isAnyGroup() || studentService.isAnyStudent() || rosterService.isAnyCourseRosters();
	}

}
