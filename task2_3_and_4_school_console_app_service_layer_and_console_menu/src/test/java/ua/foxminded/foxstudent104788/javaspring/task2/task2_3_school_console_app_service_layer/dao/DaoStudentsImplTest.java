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

import ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.dao.DaoGroups;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.dao.impl.DaoGroupsJdbc;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.dao.impl.DaoStudentsJdbc;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.models.Group;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.models.Student;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = { "/scripts/table_creation_script.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class DaoStudentsImplTest {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	private DaoStudentsJdbc daoStudents;

	@BeforeEach
	void beforeAll() {
		daoStudents = new DaoStudentsJdbc(jdbcTemplate);
	}

	@Test
	void testSaveStudent() {
		Student student = new Student(null, "nick", "Svarovski", null);
		int status = daoStudents.save(student);
		assertTrue(status > 0);
	}

	@Test
	void testGetInteger() {
		List<Student> students = new ArrayList<>();

		students.add(new Student(1, "Pavel", "Pashkin", null));
		students.add(new Student(2, "Piotr", "Vasiuk", null));
		students.add(new Student(3, "Tasas", "Mirko", null));

		for (Student student : students) {
			daoStudents.save(student);
		}

		Student actual = daoStudents.get(3).get();

		String expected = "Mirko";

		assertEquals(expected, actual.getLastName());
	}

	@Test
	void testGetListOfInteger() {
		List<Student> students = new ArrayList<>();

		students.add(new Student(1, "Pavel", "Pashkin", null));
		students.add(new Student(2, "Piotr", "Vasiuk", null));
		students.add(new Student(3, "Tasas", "Mirko", null));

		for (Student student : students) {
			daoStudents.save(student);
		}

		List<Integer> ids = new ArrayList<>();
		ids.add(1);
		ids.add(2);
		ids.add(3);

		List<Student> actual = daoStudents.get(ids);

		String expected = "Mirko";

		assertEquals(expected, actual.get(2).getLastName());
	}

	@Test
	void testGetAll() {
		List<Student> students = new ArrayList<>();

		students.add(new Student(null, "Pavel", "Pashkin", null));
		students.add(new Student(null, "Piotr", "Vasiuk", null));
		students.add(new Student(null, "Tasas", "Mirko", null));

		for (Student student : students) {
			daoStudents.save(student);
		}
		int actual = daoStudents.getAll().size();

		int expected = 3;

		assertEquals(expected, actual);
	}

	@Test
	void testDelete() {
		List<Student> students = new ArrayList<>();

		students.add(new Student(null, "Pavel", "Pashkin", null));
		students.add(new Student(null, "Piotr", "Vasiuk", null));
		students.add(new Student(null, "Tasas", "Mirko", null));

		for (Student student : students) {
			daoStudents.save(student);
		}

		daoStudents.delete(new Student(2, null, null, null));

		int actual = daoStudents.getAll().size();

		int expected = 2;

		assertEquals(expected, actual);
	}

	@Test
	void testGetByGroupCount() {
		DaoGroups daoGroups = new DaoGroupsJdbc(jdbcTemplate);
		List<Group> groups = new ArrayList<>();

		groups.add(new Group(1, "xx-11"));
		groups.add(new Group(2, "xy-15"));
		groups.add(new Group(3, "xz-16"));

		for (Group group : groups) {
			daoGroups.save(group);
		}
		List<Student> students = new ArrayList<>();

		students.add(new Student(null, "Pavel", "Pashkin", 1));
		students.add(new Student(null, "Piotr", "Vasiuk", 2));
		students.add(new Student(null, "Tasas", "Mirko", 2));
		students.add(new Student(null, "Tasas", "Zubko", 2));
		students.add(new Student(null, "Sem", "Milek", 3));
		students.add(new Student(null, "Marek", "Rak", 3));

		for (Student student : students) {
			daoStudents.save(student);
		}

		List<Integer> actual = daoStudents.getGroupSIdCount(2);

		Integer expected = 3;
		assertEquals(expected, actual.get(0));
	}

}
