package ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.dao.impl.DaoCoursesJdbc;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.models.Course;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = { "/scripts/table_creation_script.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class DaoCoursesImplTest {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	private DaoCoursesJdbc daoCourses;

	@BeforeEach
	void beforeAll() {
		daoCourses = new DaoCoursesJdbc(jdbcTemplate);
	}

	@Test
	void testSaveCourse() {
		Course course = new Course(null, "biology", "anatomia Course");

		int actual = daoCourses.save(course);

		assertTrue(actual > 0);
	}

	@Test
	void testGet() {
		List<Course> courses = new ArrayList<>();

		courses.add(new Course(1, "biology", "anatomia Course"));
		courses.add(new Course(2, "math", "math Course"));
		courses.add(new Course(3, "art", "art Course"));

		for (Course course : courses) {
			daoCourses.save(course);
		}
		
		Course actual = daoCourses.get(3).get();
		String expected = "art";

		assertEquals(expected, actual.getCourseName());

	}

	@Test
	void testGetAll() {
		List<Course> courses = new ArrayList<>();

		courses.add(new Course(1, "biology", "anatomia Course"));
		courses.add(new Course(2, "math", "math Course"));
		courses.add(new Course(3, "art", "art Course"));

		for (Course course : courses) {
			daoCourses.save(course);
		}
		
		int actual = daoCourses.getAll().size();
		int expected = 3;

		assertEquals(expected, actual);
	}

	@Test
	void testGetByName() {
		List<Course> courses = new ArrayList<>();

		courses.add(new Course(null, "biology", "anatomia Course"));
		courses.add(new Course(null, "math", "math Course"));
		courses.add(new Course(null, "art", "art Course"));

		for (Course course : courses) {
			daoCourses.save(course);
		}
		

		Course actual = daoCourses.getByName("math").get();
		String expected = "math Course";

		assertEquals(expected, actual.getCourseDescription());
	}

	@Test
	void testDelete() {
		List<Course> courses = new ArrayList<>();

		courses.add(new Course(1, "biology", "anatomia Course"));
		courses.add(new Course(2, "math", "math Course"));
		courses.add(new Course(3, "art", "art Course"));

		for (Course course : courses) {
			daoCourses.save(course);
		}
		

		daoCourses.delete(new Course(2, null, null));

		int actual = daoCourses.getAll().size();
		int expected = 2;

		assertEquals(expected, actual);
	}

	
}
