package ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.dao.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;

import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.dao.DaoConnectionHolder;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.dao.DaoCourses;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.dao.impl.DaoConnectionHolderImpl;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.dao.impl.DaoCoursesImpl;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.models.Course;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.models.DaoConnectionSettings;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.services.PathStorage;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.services.ResourceLoader;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.services.impl.PathStorageImpl;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.services.impl.ResourceLoaderImpl;

class DaoCoursesImplTest {

	static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine");

	DaoConnectionHolder connection;

	@BeforeAll
	static void beforeAll() {
		postgres.start();
	}

	@AfterAll
	static void afterAll() {
		postgres.stop();
	}

	@BeforeEach
	void setting() {
		DaoConnectionSettings daoConnectionSettings = new DaoConnectionSettings(postgres.getJdbcUrl(),
				postgres.getUsername(), postgres.getPassword());
		connection = new DaoConnectionHolderImpl(daoConnectionSettings);
	}

	@Test
	void testAddCourses() {
		PathStorage pathStorage = new PathStorageImpl(Paths.get("src", "main", "resourses"));

		connection.executeScript(pathStorage.getCreateTableScriptPath());
		DaoCourses daoCourses = new DaoCoursesImpl(connection);

		List<Course> courses = new ArrayList<>();

		courses.add(new Course(null, "biology", "anatomia Course"));
		courses.add(new Course(null, "math", "math Course"));
		courses.add(new Course(null, "art", "art Course"));

		int actual = daoCourses.addCourses(courses);

		assertTrue(actual > 0);
	}

	@Test
	void testAddCourse() {
		PathStorage pathStorage = new PathStorageImpl(Paths.get("src", "main", "resourses"));

		connection.executeScript(pathStorage.getCreateTableScriptPath());
		DaoCourses daoCourses = new DaoCoursesImpl(connection);

		Course course = new Course(null, "biology", "anatomia Course");

		int actual = daoCourses.addCourse(course);

		assertTrue(actual > 0);
	}

	@Test
	void testGetByName() {
		PathStorage pathStorage = new PathStorageImpl(Paths.get("src", "main", "resourses"));

		connection.executeScript(pathStorage.getCreateTableScriptPath());
		DaoCourses daoCourses = new DaoCoursesImpl(connection);

		List<Course> courses = new ArrayList<>();

		courses.add(new Course(null, "biology", "anatomia Course"));
		courses.add(new Course(null, "math", "math Course"));
		courses.add(new Course(null, "art", "art Course"));

		daoCourses.addCourses(courses);

		String actual = daoCourses.getByName("biology").getCourseDescription();

		String expected = "anatomia Course";

		// assertEquals(expected, actual);
	}

	@Test
	void testGetCourses() {
		PathStorage pathStorage = new PathStorageImpl(Paths.get("src", "main", "resourses"));

		connection.executeScript(pathStorage.getCreateTableScriptPath());
		DaoCourses daoCourses = new DaoCoursesImpl(connection);

		List<Course> courses = new ArrayList<>();

		courses.add(new Course(null, "biology", "anatomia Course"));
		courses.add(new Course(null, "math", "math Course"));
		courses.add(new Course(null, "art", "art Course"));

		daoCourses.addCourses(courses);

		List<Course> actual = daoCourses.getCourses();

		List<Course> expected = new ArrayList<>();

		expected.add(new Course(1, "biology", "anatomia Course"));
		expected.add(new Course(2, "math", "math Course"));
		expected.add(new Course(3, "art", "art Course"));

		assertTrue(actual.size() == 3);
	}

	@Test
	void testGetTable() {
		List<Course> courses = new ArrayList<>();

		courses.add(new Course(1, "biology", "anatomia Course"));
		courses.add(new Course(2, "math", "math Course"));
		courses.add(new Course(3, "art", "art Course"));
		List<String[]> actual = new DaoCoursesImpl(null).getTable(courses);

		List<String[]> expected = new ArrayList<>();
		expected.add(new String[] { "course_id", "course_name", "course_description" });
		expected.add(new String[] { "1", "biology", "anatomia Course" });
		expected.add(new String[] { "2", "math", "math Course" });
		expected.add(new String[] { "3", "art", "art Course" });
		assertEquals(expected.get(2)[2], actual.get(2)[2]);
	}

}
