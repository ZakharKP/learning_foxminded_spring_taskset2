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

import ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.dao.impl.DaoStudentsImpl;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.models.ForTestsEntitiesCreator;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.models.Student;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
		DaoStudentsImpl.class }))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = { "/scripts/table_creation_script.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class DaoStudentsJPATest {

	@Autowired
	private DaoStudentsImpl daoStudents;

	

	@Test
	void testSave() {
		Student student = Student.builder().firstName("John").lastName("Doe").build();

		int actual = daoStudents.save(student);

		assertTrue(actual > 0);

	}

	@Test
	void testGetInteger() {
		List<Student> students = ForTestsEntitiesCreator.getNewStudents();

		for (Student student : students) {
			daoStudents.save(student);
		}

		Student actual = daoStudents.get( 3).get();

		Student expected = ForTestsEntitiesCreator.getStudents().get(2);

		assertEquals(expected, actual);
	}

	@Test
	void testGetAll() {
		List<Student> students = ForTestsEntitiesCreator.getNewStudents();
		List<Student> expected = ForTestsEntitiesCreator.getStudents();

		for (Student student : students) {
			daoStudents.save(student);
		}

		List<Student> actual = daoStudents.getAll();

		assertEquals(expected, actual);
	}

	@Test
	void testDelete() {
		List<Student> students = ForTestsEntitiesCreator.getNewStudents();
		List<Student> expected = ForTestsEntitiesCreator.getStudents();

		for (Student student : students) {
			daoStudents.save(student);
		}
		int status = daoStudents.delete(students.get(1));

		if (status > 0) {
			expected.remove(1);
		}

		List<Student> actual = daoStudents.getAll();

		assertEquals(expected, actual);
	}

	@Test
	void Update() {
		List<Student> students = ForTestsEntitiesCreator.getNewStudents();

		for (Student student : students) {
			daoStudents.save(student);
		}

		Student expected = ForTestsEntitiesCreator.getStudents().get(1);
		expected.setFirstName("Mikajanjaka");

		int status = daoStudents.update(expected);

		Student actual = null;

		if (status > 0) {
			actual = daoStudents.get(expected.getId()).get();
		}

		assertEquals(expected, actual);
	}

		@Test
	void testSize() {
		List<Student> students = ForTestsEntitiesCreator.getNewStudents();

		for (Student student : students) {
			daoStudents.save(student);
		}

		long actual = daoStudents.size();

		assertEquals(students.size(), actual);
	}

	

	
	
}
