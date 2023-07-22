package ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.services.testdata;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.models.Course;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.models.Group;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.models.Student;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.services.TestDataGenerator;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.services.TestDataPathStorage;

class TestDataGeneratorTest {

	private TestDataGenerator dataGenerator;

	@BeforeEach
	void BeforeEach() {
		dataGenerator = new TestDataGenerator(new TestDataPathStorage());
	}

	@Test
	void testGetGroups() {

		List<Group> actual = dataGenerator.getGroups();

		int expectedSize = 10;

		assertEquals(expectedSize, actual.size());
	}

	@Test
	void testGetCourses() {
		List<Course> actual = dataGenerator.getCourses();

		int expectedSize = 10;

		assertEquals(expectedSize, actual.size());
	}

	@Test
	void testGetStudents() {
		List<Student> actual = dataGenerator.getStudents();

		int expectedSize = 200;

		assertEquals(expectedSize, actual.size());
	}

	@Test
	void testAddGroupsToStudents() {
		List<Student> students = dataGenerator.getStudents();
		List<Group> groups = dataGenerator.getGroups();

		dataGenerator.addGroupsToStudents(students, groups);

		List<Student> actual = students.stream().filter(x -> x.getGroup() != null).collect(Collectors.toList());

		assertTrue(actual.size() > 0);
	}

	@Test
	void testAddCoursesToStudents() {
		List<Student> students = dataGenerator.getStudents();
		List<Course> courses = dataGenerator.getCourses();

		dataGenerator.addCoursesToStudents(students, courses);

		List<Student> actual = students.stream().filter(x -> x.getCourses().size() > 0).collect(Collectors.toList());

		assertTrue(actual.size() > 0);
	}

	
}
