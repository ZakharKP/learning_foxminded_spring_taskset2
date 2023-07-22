package ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.dao.jpa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.jdbc.Sql;

import ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.dao.impl.DaoCoursesImpl;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.models.Course;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.models.ForTestsEntitiesCreator;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
		DaoCoursesImpl.class }))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = { "/scripts/table_creation_script.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class DaoCoursesJPATest {

	@Autowired
	private DaoCoursesImpl daoCourses;


	@Test
	void testSave() {
		Course course = ForTestsEntitiesCreator.getNewCourse();

		int actual = daoCourses.save(course);

		assertTrue(actual > 0);
	}

	@Test
	void testGet() {
		List<Course> courses = ForTestsEntitiesCreator.getNewCourses();

		for (Course course : courses) {
			daoCourses.save(course);
		}

		Course actual = daoCourses.get( 3).get();

		Course expected = ForTestsEntitiesCreator.getCourses().get(2);

		assertEquals(expected, actual);
	}

	@Test
	void testGetAll() {
		List<Course> courses = ForTestsEntitiesCreator.getNewCourses();
		List<Course> expected = ForTestsEntitiesCreator.getCourses();

		for (Course course : courses) {
			daoCourses.save(course);
		}

		List<Course> actual = daoCourses.getAll();

		assertEquals(expected, actual);
	}

	@Test
	void testGetByName() {
		List<Course> courses = ForTestsEntitiesCreator.getNewCourses();
		Course expected = ForTestsEntitiesCreator.getCourses().stream().filter(x -> x.getCourseName().equals("math")).findFirst().get();

		for (Course course : courses) {
			daoCourses.save(course);
		}

		Course actual = daoCourses.getByName("math").get();

		assertEquals(expected, actual);
	}

	@Test
	void testDelete() {
		List<Course> courses = ForTestsEntitiesCreator.getNewCourses();
		List<Course> expected = ForTestsEntitiesCreator.getCourses();

		for (Course course : courses) {
			daoCourses.save(course);
		}

		int status = daoCourses.delete(courses.get(1));

		if (status > 0) {
			expected.remove(1);
		}

		List<Course> actual = daoCourses.getAll();

		assertEquals(expected, actual);
	}

	@Test
	void Update() {
		List<Course> courses = ForTestsEntitiesCreator.getNewCourses();

		for (Course course : courses) {
			daoCourses.save(course);
		}

		Course expected = ForTestsEntitiesCreator.getCourses().get(1);
		expected.setCourseName("foxminded");

		int status = daoCourses.update(expected);

		Course actual = null;

		if (status > 0) {
			actual = daoCourses.get(expected.getId()).get();
		}

		assertEquals(expected, actual);
	}

	@Test
	void testSize() {
		List<Course> courses = ForTestsEntitiesCreator.getNewCourses();

		for (Course course : courses) {
			daoCourses.save(course);
		}

		long actual = daoCourses.size();

		assertEquals(courses.size(), actual);
	}

	
	

}
