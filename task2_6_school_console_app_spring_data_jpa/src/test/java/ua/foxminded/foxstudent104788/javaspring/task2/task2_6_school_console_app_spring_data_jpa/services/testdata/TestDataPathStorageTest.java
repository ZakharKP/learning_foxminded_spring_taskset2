package ua.foxminded.foxstudent104788.javaspring.task2.task2_6_school_console_app_spring_data_jpa.services.testdata;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import ua.foxminded.foxstudent104788.javaspring.task2.task2_6_school_console_app_spring_data_jpa.services.TestDataPathStorage;

class TestDataPathStorageTest {

	@Test
	void testGetTestCourses() {

		Path actual = new TestDataPathStorage().getTestCourses();

		Path expected = Paths.get("src", "main", "resources", "testData", "courses.properties");

		assertEquals(expected, actual);
	}

	@Test
	void testGetTestFirstNames() {

		Path actual = new TestDataPathStorage().getTestFirstNames();

		Path expected = Paths.get("src", "main", "resources", "testData", "firstNamesStudents.txt");

		assertEquals(expected, actual);

	}

	@Test
	void testGetTestLastNames() {

		Path actual = new TestDataPathStorage().getTestLastNames();

		Path expected = Paths.get("src", "main", "resources", "testData", "lastNamesStudents.txt");

		assertEquals(expected, actual);
	}

}
