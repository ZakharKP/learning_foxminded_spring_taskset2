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
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.dao.DaoStudents;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.dao.impl.DaoConnectionHolderImpl;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.dao.impl.DaoGroupsImpl;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.dao.impl.DaoStudentsImpl;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.models.DaoConnectionSettings;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.models.Group;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.models.Student;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.services.PathStorage;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.services.ResourceLoader;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.services.impl.PathStorageImpl;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.services.impl.ResourceLoaderImpl;

class DaoStudentsImplTest {

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
	void testAddStudents() {
		PathStorage pathStorage = new PathStorageImpl(Paths.get("src", "main", "resourses"));

		connection.executeScript(pathStorage.getCreateTableScriptPath());
		DaoStudents daoStudents = new DaoStudentsImpl(connection);

		List<Student> students = new ArrayList<>();

		students.add(new Student(null, "Pavel", "Pashkin", null));
		students.add(new Student(null, "Piotr", "Vasiuk", null));
		students.add(new Student(null, "Tasas", "Mirko", null));

		int actual = daoStudents.addStudents(students);

		assertTrue(actual > 0);
	}

	@Test
	void testAddStudent() {
		PathStorage pathStorage = new PathStorageImpl(Paths.get("src", "main", "resourses"));

		connection.executeScript(pathStorage.getCreateTableScriptPath());
		DaoStudents daoStudents = new DaoStudentsImpl(connection);

		Student student = new Student(null, "Pavel", "Pashkin", null);

		int actual = daoStudents.addStudent(student);

		assertTrue(actual > 0);
	}

	@Test
	void testGetById() {
		PathStorage pathStorage = new PathStorageImpl(Paths.get("src", "main", "resourses"));
		
		connection.executeScript(pathStorage.getCreateTableScriptPath());
		DaoStudents daoStudents = new DaoStudentsImpl(connection);

		List<Student> students = new ArrayList<>();

		students.add(new Student(1, "Pavel", "Pashkin", null));
		students.add(new Student(2, "Piotr", "Vasiuk", null));
		students.add(new Student(3, "Tasas", "Mirko", null));

		daoStudents.addStudents(students);

		List<Integer> ids = new ArrayList<>();
		ids.add(1);
		ids.add(2);
		ids.add(3);

		List<Student> actual = daoStudents.getById(ids);

		String expected = "Mirko";

		assertEquals(expected, actual.get(2).getLastName());
	}

	@Test
	void testGetTable() {
		List<Student> students = new ArrayList<>();

		students.add(new Student(1, "Pavel", "Pashkin", null));
		students.add(new Student(2, "Piotr", "Vasiuk", 2));
		students.add(new Student(3, "Tasas", "Mirko", 2));
		List<String[]> actual = new DaoStudentsImpl(null).getTable(students);

		List<String[]> expected = new ArrayList<>();
		expected.add(new String[] { "student_id", "first_name", "last_name", "group_id" });
		expected.add(new String[] { "1", "Pavel", "Pashkin", null });
		expected.add(new String[] { "2", "Piotr", "Vasiuk", "2" });
		expected.add(new String[] { "3", "Tasas", "Mirko", "2" });
		assertEquals(expected.get(2)[2], actual.get(2)[2]);
	}

	@Test
	void testDelete() {
		PathStorage pathStorage = new PathStorageImpl(Paths.get("src", "main", "resourses"));
		
		connection.executeScript(pathStorage.getCreateTableScriptPath());
		DaoStudents daoStudents = new DaoStudentsImpl(connection);

		List<Student> students = new ArrayList<>();

		students.add(new Student(1, "Pavel", "Pashkin", null));
		students.add(new Student(2, "Piotr", "Vasiuk", null));
		students.add(new Student(3, "Tasas", "Mirko", null));

		daoStudents.addStudents(students);

		List<Integer> ids = new ArrayList<>();
		ids.add(1);
		ids.add(2);
		ids.add(3);

		int status = daoStudents.delete(students.get(1));

		List<Student> actual = daoStudents.getById(ids);

		assertTrue(status > 0);
		assertTrue(actual.size() == 2);
	}

	@Test
	void testGetByGroupCount() {
		PathStorage pathStorage = new PathStorageImpl(Paths.get("src", "main", "resourses"));
		
		connection.executeScript(pathStorage.getCreateTableScriptPath());
		DaoStudents daoStudents = new DaoStudentsImpl(connection);

		List<Group> groups = new ArrayList<>();

		groups.add(new Group(null, "xx-11"));
		groups.add(new Group(null, "xy-15"));
		groups.add(new Group(null, "xz-16"));

		new DaoGroupsImpl(connection).addGroups(groups);

		List<Student> students = new ArrayList<>();

		students.add(new Student(null, "Pavel", "Pashkin", 1));
		students.add(new Student(null, "Piotr", "Vasiuk", 2));
		students.add(new Student(null, "Tasas", "Mirko", 2));
		students.add(new Student(null, "Tasas", "Zubko", 2));
		students.add(new Student(null, "Sem", "Milek", 3));
		students.add(new Student(null, "Marek", "Rak", 3));

		daoStudents.addStudents(students);

		List<Student> actual = daoStudents.getGroupIdByGroupCount(2);

		Integer expected = 3;
		assertEquals(expected, actual.get(0).getGroupId());
	}

}
