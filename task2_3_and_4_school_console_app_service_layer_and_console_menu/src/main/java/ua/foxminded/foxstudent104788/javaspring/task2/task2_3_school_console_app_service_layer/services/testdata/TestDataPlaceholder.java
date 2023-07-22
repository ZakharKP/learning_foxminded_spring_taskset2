package ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.services.testdata;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.models.Course;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.models.CourseRoster;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.models.Group;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.models.Student;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.services.entityservices.CourseRosterService;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.services.entityservices.CourseService;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.services.entityservices.GroupService;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.services.entityservices.StudentService;

/**
 * The TestDataPlaceholder class is responsible for filling in tables with test
 * data.
 */
@AllArgsConstructor
@Service
@Log4j2
public class TestDataPlaceholder {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private StudentService studentService;
	@Autowired
	private GroupService groupService;
	@Autowired
	private CourseRosterService rosterService;
	@Autowired
	private CourseService courseService;

	/**
	 * Generates test data by refreshing tables and filling them with data.
	 */
	public void generateData() {

		refreshTables();

		TestDataPathStorage pathStorage = new TestDataPathStorage();
		TestDataGenerator dataGenerator = new TestDataGenerator(pathStorage);

		log.info("generating data");
		List<Group> groups = dataGenerator.getGroups();
		List<Course> courses = dataGenerator.getCourses();
		List<Student> students = dataGenerator.getStudents();
		List<CourseRoster> courseRosters = dataGenerator.getCourseRosters();

		log.info("data was generated, start updating of database");
		fillInDataBase(groups, courses, students, courseRosters);
		log.info("database was update with new test data sucessfully");
	}

	/**
	 * Refreshes the tables by deleting all existing data and resetting the
	 * sequences.
	 */
	private void refreshTables() {

		log.info("Starting cleaning of tables ");

		jdbcTemplate.update("DELETE FROM course_rosters");
		jdbcTemplate.update("DELETE FROM courses");
		jdbcTemplate.update("DELETE FROM students");
		jdbcTemplate.update("DELETE FROM groups");

		jdbcTemplate.update("ALTER SEQUENCE course_rosters_course_roster_id_seq RESTART WITH 1");
		jdbcTemplate.update("ALTER SEQUENCE courses_course_id_seq RESTART WITH 1");
		jdbcTemplate.update("ALTER SEQUENCE students_student_id_seq RESTART WITH 1");
		jdbcTemplate.update("ALTER SEQUENCE groups_group_id_seq RESTART WITH 1");

		log.info("database was cleaned");
	}

	/**
	 * Fills in the database tables with the provided data.
	 *
	 * @param groups        The list of groups to be inserted into the groups table.
	 * @param courses       The list of courses to be inserted into the courses
	 *                      table.
	 * @param students      The list of students to be inserted into the students
	 *                      table.
	 * @param courseRosters The list of course rosters to be inserted into the
	 *                      course_rosters table.
	 */
	private void fillInDataBase(List<Group> groups, List<Course> courses, List<Student> students,
			List<CourseRoster> courseRosters) {
		// 1
		fillInCoursesTable(courses);
		// 2
		fillInGroupTable(groups);
		// 3
		fillInStudentTable(students);
		// 4
		fillInCourseRostersTable(courseRosters);

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
		
		log.info("Starting fill in students table");
		studentService.saveAllStudents(students);

	}

	private void fillInCourseRostersTable(List<CourseRoster> courseRosters) {
		
		log.info("Starting fill in course_rosters table");
		rosterService.saveAll(courseRosters);

	}

}
