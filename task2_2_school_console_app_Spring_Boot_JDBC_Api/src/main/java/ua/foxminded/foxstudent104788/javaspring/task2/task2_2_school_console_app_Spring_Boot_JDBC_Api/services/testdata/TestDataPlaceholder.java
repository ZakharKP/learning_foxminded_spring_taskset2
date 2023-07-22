package ua.foxminded.foxstudent104788.javaspring.task2.task2_2_school_console_app_Spring_Boot_JDBC_Api.services.testdata;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import lombok.AllArgsConstructor;
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
import ua.foxminded.foxstudent104788.javaspring.task2.task2_2_school_console_app_Spring_Boot_JDBC_Api.models.Student;

/**
 * The TestDataPlaceholder class is responsible for filling in tables with test
 * data.
 */
@AllArgsConstructor
public class TestDataPlaceholder {

	private JdbcTemplate jdbcTemplate;

	/**
	 * Generates test data by refreshing tables and filling them with data.
	 */
	public void generateData() {

		refreshTables();

		TestDataPathStorage pathStorage = new TestDataPathStorage();
		TestDataGenerator dataGenerator = new TestDataGenerator(pathStorage);
		List<Group> groups = dataGenerator.getGroups();
		List<Course> courses = dataGenerator.getCourses();
		List<Student> students = dataGenerator.getStudents();
		List<CourseRoster> courseRosters = dataGenerator.getCourseRosters();

		fillInDataBase(groups, courses, students, courseRosters);
	}

	/**
	 * Refreshes the tables by deleting all existing data and resetting the
	 * sequences.
	 */
	private void refreshTables() {

		jdbcTemplate.update("DELETE FROM course_rosters");
		jdbcTemplate.update("DELETE FROM courses");
		jdbcTemplate.update("DELETE FROM students");
		jdbcTemplate.update("DELETE FROM groups");

		jdbcTemplate.update("ALTER SEQUENCE course_rosters_course_roster_id_seq RESTART WITH 1");
		jdbcTemplate.update("ALTER SEQUENCE courses_course_id_seq RESTART WITH 1");
		jdbcTemplate.update("ALTER SEQUENCE students_student_id_seq RESTART WITH 1");
		jdbcTemplate.update("ALTER SEQUENCE groups_group_id_seq RESTART WITH 1");

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
		DaoCourses daoCourses = new DaoCoursesJdbc(jdbcTemplate);

		for (Course course : courses) {
			daoCourses.save(course);
		}
	}

	private void fillInGroupTable(List<Group> groups) {
		DaoGroups daoGroups = new DaoGroupsJdbc(jdbcTemplate);

		for (Group group : groups) {
			daoGroups.save(group);
		}
	}

	private void fillInStudentTable(List<Student> students) {

		DaoStudents daoStudents = new DaoStudentsJdbc(jdbcTemplate);
		for (Student student : students) {
			daoStudents.save(student);
		}
	}

	private void fillInCourseRostersTable(List<CourseRoster> courseRosters) {
		DaoCourseRosters daoCourseRosters = new DaoCourseRostersJdbc(jdbcTemplate);
		for (CourseRoster courseRoster : courseRosters) {
			daoCourseRosters.save(courseRoster);
		}

	}

}
