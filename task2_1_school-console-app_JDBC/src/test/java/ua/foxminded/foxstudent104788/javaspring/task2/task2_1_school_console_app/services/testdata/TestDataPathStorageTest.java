package ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.services.testdata;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.services.testdata.TestDataPathStorage;

class TestDataPathStorageTest {

	@Test
	void testGetTestCourses() {
		Path pathDir = Paths.get("src", "main", "resourses");

		Path actual = new TestDataPathStorage(pathDir).getTestCourses();

		Path expected = Paths.get("src", "main", "resourses", "testData", "courses.properties");

		assertEquals(expected, actual);
	}

	@Test
	void testGetTestFirstNames() {
		Path pathDir = Paths.get("src", "main", "resourses");

		Path actual = new TestDataPathStorage(pathDir).getTestFirstNames();

		Path expected = Paths.get("src", "main", "resourses", "testData", "firstNamesStudents.txt");

		assertEquals(expected, actual);

	}

	@Test
	void testGetTestLastNames() {
		Path pathDir = Paths.get("src", "main", "resourses");

		Path actual = new TestDataPathStorage(pathDir).getTestLastNames();

		Path expected = Paths.get("src", "main", "resourses", "testData", "lastNamesStudents.txt");

		assertEquals(expected, actual);
	}

}
