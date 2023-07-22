package ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.dao.impl;

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
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.dao.DaoEnrollments;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.dao.impl.DaoConnectionHolderImpl;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.dao.impl.DaoCoursesImpl;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.dao.impl.DaoEnrollmentsIpml;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.dao.impl.DaoStudentsImpl;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.models.Course;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.models.DaoConnectionSettings;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.models.Enrollment;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.models.Student;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.services.PathStorage;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.services.ResourceLoader;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.services.impl.PathStorageImpl;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.services.impl.ResourceLoaderImpl;

class DaoEnrollmentsIpmlTest {

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
	void testAddEnrollments() {
		PathStorage pathStorage = new PathStorageImpl(Paths.get("src", "main", "resourses"));

		connection.executeScript(pathStorage.getCreateTableScriptPath());
		List<Course> courses = new ArrayList<>();

		courses.add(new Course(null, "biology", "anatomia Course"));
		courses.add(new Course(null, "math", "math Course"));
		courses.add(new Course(null, "art", "art Course"));

		new DaoCoursesImpl(connection).addCourses(courses);

		List<Student> students = new ArrayList<>();

		students.add(new Student(null, "Pavel", "Pashkin", null));
		students.add(new Student(null, "Piotr", "Vasiuk", null));
		students.add(new Student(null, "Tasas", "Mirko", null));

		new DaoStudentsImpl(connection).addStudents(students);

		DaoEnrollments daoEnrollments = new DaoEnrollmentsIpml(connection);

		List<Enrollment> enrollments = new ArrayList<>();

		enrollments.add(new Enrollment(null, 1, 2));
		enrollments.add(new Enrollment(null, 1, 3));
		enrollments.add(new Enrollment(null, 3, 2));

		int actual = daoEnrollments.addEnrollments(enrollments);

		assertTrue(actual > 0);
	}

	@Test
	void testAddEnrollment() {
		PathStorage pathStorage = new PathStorageImpl(Paths.get("src", "main", "resourses"));

		connection.executeScript(pathStorage.getCreateTableScriptPath());
		List<Course> courses = new ArrayList<>();

		courses.add(new Course(null, "biology", "anatomia Course"));
		courses.add(new Course(null, "math", "math Course"));
		courses.add(new Course(null, "art", "art Course"));

		new DaoCoursesImpl(connection).addCourses(courses);

		List<Student> students = new ArrayList<>();

		students.add(new Student(null, "Pavel", "Pashkin", null));
		students.add(new Student(null, "Piotr", "Vasiuk", null));
		students.add(new Student(null, "Tasas", "Mirko", null));

		new DaoStudentsImpl(connection).addStudents(students);

		DaoEnrollments daoEnrollments = new DaoEnrollmentsIpml(connection);

		Enrollment enrollment = new Enrollment(null, 1, 2);

		int actual = daoEnrollments.addEnrollment(enrollment);

		assertTrue(actual > 0);
	}

	@Test
	void testGetByCourseId() {
		PathStorage pathStorage = new PathStorageImpl(Paths.get("src", "main", "resourses"));

		connection.executeScript(pathStorage.getCreateTableScriptPath());
		List<Course> courses = new ArrayList<>();

		courses.add(new Course(null, "biology", "anatomia Course"));
		courses.add(new Course(null, "math", "math Course"));
		courses.add(new Course(null, "art", "art Course"));

		new DaoCoursesImpl(connection).addCourses(courses);

		List<Student> students = new ArrayList<>();

		students.add(new Student(null, "Pavel", "Pashkin", null));
		students.add(new Student(null, "Piotr", "Vasiuk", null));
		students.add(new Student(null, "Tasas", "Mirko", null));

		new DaoStudentsImpl(connection).addStudents(students);

		DaoEnrollments daoEnrollments = new DaoEnrollmentsIpml(connection);

		List<Enrollment> enrollments = new ArrayList<>();

		enrollments.add(new Enrollment(null, 1, 2));
		enrollments.add(new Enrollment(null, 1, 3));
		enrollments.add(new Enrollment(null, 3, 2));

		daoEnrollments.addEnrollments(enrollments);

		List<Enrollment> actual = daoEnrollments.getByCourseId(2);

		assertTrue(actual.size() == 2);
	}

	@Test
	void testDelete() {
		PathStorage pathStorage = new PathStorageImpl(Paths.get("src", "main", "resourses"));

		connection.executeScript(pathStorage.getCreateTableScriptPath());
		List<Course> courses = new ArrayList<>();

		courses.add(new Course(null, "biology", "anatomia Course"));
		courses.add(new Course(null, "math", "math Course"));
		courses.add(new Course(null, "art", "art Course"));

		new DaoCoursesImpl(connection).addCourses(courses);

		List<Student> students = new ArrayList<>();

		students.add(new Student(null, "Pavel", "Pashkin", null));
		students.add(new Student(null, "Piotr", "Vasiuk", null));
		students.add(new Student(null, "Tasas", "Mirko", null));

		new DaoStudentsImpl(connection).addStudents(students);

		DaoEnrollments daoEnrollments = new DaoEnrollmentsIpml(connection);

		List<Enrollment> enrollments = new ArrayList<>();

		enrollments.add(new Enrollment(null, 1, 2));
		enrollments.add(new Enrollment(null, 1, 3));
		enrollments.add(new Enrollment(null, 3, 2));

		daoEnrollments.addEnrollments(enrollments);

		int actual = daoEnrollments.delete(enrollments.get(1));

		assertTrue(actual > 0);
	}

}
