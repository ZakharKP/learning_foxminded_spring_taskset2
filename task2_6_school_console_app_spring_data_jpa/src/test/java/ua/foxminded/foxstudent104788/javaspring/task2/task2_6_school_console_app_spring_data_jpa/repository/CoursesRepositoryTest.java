package ua.foxminded.foxstudent104788.javaspring.task2.task2_6_school_console_app_spring_data_jpa.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.jdbc.Sql;

import ua.foxminded.foxstudent104788.javaspring.task2.task2_6_school_console_app_spring_data_jpa.models.Course;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_6_school_console_app_spring_data_jpa.models.ForTestsEntitiesCreator;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_6_school_console_app_spring_data_jpa.repository.CoursesRepository;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = { CoursesRepository.class }))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = { "/scripts/table_creation_script.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class CoursesRepositoryTest {

	@Autowired
	private CoursesRepository coursesRepository;

	@Test
	void testSave() {
		Course course = ForTestsEntitiesCreator.getNewCourse();

		coursesRepository.save(course);

		long actual = coursesRepository.count();

		assertTrue(actual > 0);
	}

	@Test
	void testGet() {
		List<Course> courses = ForTestsEntitiesCreator.getNewCourses();

		for (Course course : courses) {
			coursesRepository.save(course);
		}

		Course actual = coursesRepository.findById(3).get();

		Course expected = ForTestsEntitiesCreator.getCourses().get(2);

		assertEquals(expected, actual);
	}

	@Test
	void testGetAll() {
		List<Course> courses = ForTestsEntitiesCreator.getNewCourses();
		List<Course> expected = ForTestsEntitiesCreator.getCourses();

		for (Course course : courses) {
			coursesRepository.save(course);
		}

		List<Course> actual = coursesRepository.findAll();

		assertEquals(expected, actual);
	}

	@Test
	void testGetByName() {
		List<Course> courses = ForTestsEntitiesCreator.getNewCourses();
		Course expected = ForTestsEntitiesCreator.getCourses().stream().filter(x -> x.getCourseName().equals("math"))
				.findFirst().get();

		for (Course course : courses) {
			coursesRepository.save(course);
		}

		Course actual = coursesRepository.findByCourseNameContaining("math").get();

		assertEquals(expected, actual);
	}

	@Test
	void testDelete() {
		List<Course> courses = ForTestsEntitiesCreator.getNewCourses();
		List<Course> expected = ForTestsEntitiesCreator.getCourses();

		for (Course course : courses) {
			coursesRepository.save(course);
		}

		coursesRepository.delete(courses.get(1));

		expected.remove(1);

		List<Course> actual = coursesRepository.findAll();

		assertEquals(expected, actual);
	}

	@Test
	void Update() {
		List<Course> courses = ForTestsEntitiesCreator.getNewCourses();

		for (Course course : courses) {
			coursesRepository.save(course);
		}

		Course expected = ForTestsEntitiesCreator.getCourses().get(1);
		expected.setCourseName("foxminded");

		coursesRepository.save(expected);

		Course actual = coursesRepository.findById(expected.getId()).get();

		assertEquals(expected, actual);
	}

	@Test
	void testSize() {
		List<Course> courses = ForTestsEntitiesCreator.getNewCourses();

		for (Course course : courses) {
			coursesRepository.save(course);
		}

		long actual = coursesRepository.count();

		assertEquals(courses.size(), actual);
	}

}
