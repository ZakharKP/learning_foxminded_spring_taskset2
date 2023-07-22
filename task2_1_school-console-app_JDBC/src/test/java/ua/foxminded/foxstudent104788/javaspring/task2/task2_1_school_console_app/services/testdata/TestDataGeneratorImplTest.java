package ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.services.testdata;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.models.Course;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.models.Enrollment;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.models.Group;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.models.Student;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.services.testdata.TestDataGenerator;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.services.testdata.TestDataPathStorage;

class TestDataGeneratorImplTest {

	@Test
	void testGetGroups() {
		TestDataGenerator dataGenerator = new TestDataGenerator(
				new TestDataPathStorage(Paths.get("src", "main", "resourses")));

		List<Group> actual = dataGenerator.getGroups();

		assertTrue(actual.size() == 10);

	}

	@Test
	void testGetCourses() {
		TestDataGenerator dataGenerator = new TestDataGenerator(
				new TestDataPathStorage(Paths.get("src", "main", "resourses")));

		List<Course> actual = dataGenerator.getCourses();

		List<Course> expected = new ArrayList<>();

		expected.add(new Course(null, "math", "Mathematics course"));
		expected.add(new Course(null, "biology", "Biology course about nature"));
		expected.add(new Course(null, "physics", "Physics course"));
		expected.add(new Course(null, "chemistry", "Chemistry course"));
		expected.add(new Course(null, "history", "History course"));
		expected.add(new Course(null, "english", "English language course"));
		expected.add(new Course(null, "programming", "Programming course"));
		expected.add(new Course(null, "music", "Music appreciation course"));
		expected.add(new Course(null, "art", "Art history course"));
		expected.add(new Course(null, "economics", "Economics course"));

		assertTrue(actual.containsAll(expected) && actual.size() == 10);

	}

	@Test
	void testGetStudents() {
		TestDataGenerator dataGenerator = new TestDataGenerator(
				new TestDataPathStorage(Paths.get("src", "main", "resourses")));

		List<Student> actual = dataGenerator.getStudents();

		assertTrue(actual.size() == 200);

	}

	@Test
	void testGetEnrollments() {
		TestDataGenerator dataGenerator = new TestDataGenerator(
				new TestDataPathStorage(Paths.get("src", "main", "resourses")));

		List<Enrollment> actual = dataGenerator.getEnrollments();

		assertFalse(actual.isEmpty() && actual.size() >= 200);
	}

}
