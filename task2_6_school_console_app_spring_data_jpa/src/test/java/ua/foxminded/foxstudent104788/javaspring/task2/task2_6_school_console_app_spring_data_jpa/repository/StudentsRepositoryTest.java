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

import ua.foxminded.foxstudent104788.javaspring.task2.task2_6_school_console_app_spring_data_jpa.models.ForTestsEntitiesCreator;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_6_school_console_app_spring_data_jpa.models.Student;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_6_school_console_app_spring_data_jpa.repository.StudentsRepository;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = { StudentsRepository.class }))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = { "/scripts/table_creation_script.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class StudentsRepositoryTest {

	@Autowired
	private StudentsRepository studentsRepository;

	@Test
	void testSave() {
		Student student = Student.builder().firstName("John").lastName("Doe").build();

		studentsRepository.save(student);
		long actual = studentsRepository.count();

		assertTrue(actual > 0);

	}

	@Test
	void testGetInteger() {
		List<Student> students = ForTestsEntitiesCreator.getNewStudents();

		for (Student student : students) {
			studentsRepository.save(student);
		}

		Student actual = studentsRepository.findById(3).get();

		Student expected = ForTestsEntitiesCreator.getStudents().get(2);

		assertEquals(expected, actual);
	}

	@Test
	void testGetAll() {
		List<Student> students = ForTestsEntitiesCreator.getNewStudents();
		List<Student> expected = ForTestsEntitiesCreator.getStudents();

		for (Student student : students) {
			studentsRepository.save(student);
		}

		List<Student> actual = studentsRepository.findAll();

		assertEquals(expected, actual);
	}

	@Test
	void testDelete() {
		List<Student> students = ForTestsEntitiesCreator.getNewStudents();
		List<Student> expected = ForTestsEntitiesCreator.getStudents();

		for (Student student : students) {
			studentsRepository.save(student);
		}
		studentsRepository.delete(students.get(1));

		expected.remove(1);

		List<Student> actual = studentsRepository.findAll();

		assertEquals(expected, actual);
	}

	@Test
	void Update() {
		List<Student> students = ForTestsEntitiesCreator.getNewStudents();

		for (Student student : students) {
			studentsRepository.save(student);
		}

		Student expected = ForTestsEntitiesCreator.getStudents().get(1);
		expected.setFirstName("Mikajanjaka");

		studentsRepository.save(expected);

		Student actual = studentsRepository.findById(expected.getId()).get();
		

		assertEquals(expected, actual);
	}

	@Test
	void testSize() {
		List<Student> students = ForTestsEntitiesCreator.getNewStudents();

		for (Student student : students) {
			studentsRepository.save(student);
		}

		long actual = studentsRepository.count();

		assertEquals(students.size(), actual);
	}

}
