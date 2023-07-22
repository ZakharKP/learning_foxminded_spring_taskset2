package ua.foxminded.foxstudent104788.javaspring.task2.task2_2_school_console_app_Spring_Boot_JDBC_Api.dao;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import ua.foxminded.foxstudent104788.javaspring.task2.task2_2_school_console_app_Spring_Boot_JDBC_Api.dao.DaoCourses;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_2_school_console_app_Spring_Boot_JDBC_Api.dao.DaoStudents;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_2_school_console_app_Spring_Boot_JDBC_Api.dao.impl.DaoCourseRostersJdbc;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_2_school_console_app_Spring_Boot_JDBC_Api.dao.impl.DaoCoursesJdbc;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_2_school_console_app_Spring_Boot_JDBC_Api.dao.impl.DaoStudentsJdbc;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_2_school_console_app_Spring_Boot_JDBC_Api.models.Course;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_2_school_console_app_Spring_Boot_JDBC_Api.models.CourseRoster;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_2_school_console_app_Spring_Boot_JDBC_Api.models.Student;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = { "/scripts/table_creation_script.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class DaoCourseRostersIpmlTest {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	private DaoCourseRostersJdbc daoCourseRosters;

	@BeforeEach
	void beforeEach() {
		DaoCourses daoCourses;
		DaoStudents daoStudents;

		daoStudents = new DaoStudentsJdbc(jdbcTemplate);
		daoCourses = new DaoCoursesJdbc(jdbcTemplate);
		daoCourseRosters = new DaoCourseRostersJdbc(jdbcTemplate);

		List<Course> courses = new ArrayList<>();

		courses.add(new Course(1, "biology", "anatomia Course"));
		courses.add(new Course(2, "math", "math Course"));
		courses.add(new Course(3, "art", "art Course"));

		for (Course course : courses) {
			daoCourses.save(course);
		}

		List<Student> students = new ArrayList<>();

		students.add(new Student(1, "Pavel", "Pashkin", null));
		students.add(new Student(2, "Piotr", "Vasiuk", null));
		students.add(new Student(3, "Tasas", "Mirko", null));

		for (Student student : students) {
			daoStudents.save(student);
		}

	}

	@Test
	void testSaveCourseRoster() {
		CourseRoster courseRoster = new CourseRoster(null, 2, 3);

		int actual = daoCourseRosters.save(courseRoster);

		assertTrue(actual > 0);

	}

	@Test
	void testGet() {
		List<CourseRoster> courseRosters = new ArrayList<>();

		courseRosters.add(new CourseRoster(1, 1, 2));
		courseRosters.add(new CourseRoster(2, 1, 3));
		courseRosters.add(new CourseRoster(3, 3, 2));

		for (CourseRoster courseRoster : courseRosters) {
			daoCourseRosters.save(courseRoster);
		}

		CourseRoster actual = daoCourseRosters.get(2).get();

		int expected = 3;

		assertEquals(expected, actual.getCourseId());
	}

	@Test
	void testGetAll() {
		List<CourseRoster> courseRosters = new ArrayList<>();

		courseRosters.add(new CourseRoster(1, 1, 2));
		courseRosters.add(new CourseRoster(2, 1, 3));
		courseRosters.add(new CourseRoster(3, 3, 2));

		for (CourseRoster courseRoster : courseRosters) {
			daoCourseRosters.save(courseRoster);
		}

		int actual = daoCourseRosters.getAll().size();

		int expected = 3;

		assertEquals(expected, actual);
	}

	@Test
	void testDelete() {
		List<CourseRoster> courseRosters = new ArrayList<>();

		courseRosters.add(new CourseRoster(1, 1, 2));
		courseRosters.add(new CourseRoster(2, 1, 3));
		courseRosters.add(new CourseRoster(3, 3, 2));

		for (CourseRoster courseRoster : courseRosters) {
			daoCourseRosters.save(courseRoster);
		}


		daoCourseRosters.delete(new CourseRoster(2, null, null));

		int actual = daoCourseRosters.getAll().size();

		int expected = 2;

		assertEquals(expected, actual);
	}

	@Test
	void testGetByCourseId() {
		List<CourseRoster> courseRosters = new ArrayList<>();

		courseRosters.add(new CourseRoster(1, 1, 2));
		courseRosters.add(new CourseRoster(2, 1, 3));
		courseRosters.add(new CourseRoster(3, 3, 2));

		for (CourseRoster courseRoster : courseRosters) {
			daoCourseRosters.save(courseRoster);
		}


		int actual = daoCourseRosters.getListOfRostersByCourseId(2).size();

		int expected = 2;

		assertEquals(expected, actual);
	}

	@Test
	void testGetTable() {
		List<CourseRoster> courseRosters = new ArrayList<>();

		courseRosters.add(new CourseRoster(1, 1, 2));
		courseRosters.add(new CourseRoster(2, 1, 3));
		courseRosters.add(new CourseRoster(3, 3, 2));

		List<String[]> actual = daoCourseRosters.representAsTable(courseRosters);

		List<String[]> expected = new ArrayList<>();
		expected.add(new String[] { "course_roaster_id", "course_id" });
		expected.add(new String[] { "1", "1", "2" });
		expected.add(new String[] { "2", "1", "3" });
		expected.add(new String[] { "3", "3", "2" });
		assertEquals(expected.get(2)[2], actual.get(2)[2]);
	}

}
