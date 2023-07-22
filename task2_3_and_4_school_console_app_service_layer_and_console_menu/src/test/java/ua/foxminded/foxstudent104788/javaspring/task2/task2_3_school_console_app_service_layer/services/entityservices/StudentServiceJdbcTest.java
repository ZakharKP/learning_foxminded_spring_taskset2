package ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.services.entityservices;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.dao.DaoCourses;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.dao.DaoStudents;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.models.Course;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.models.Student;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.services.entityservices.impl.CourseServiceJdbc;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.services.entityservices.impl.StudentServiceJdbc;

@SpringBootTest(classes = { StudentServiceJdbc.class })
class StudentServiceJdbcTest {

	@MockBean
	DaoStudents daoStudents;

	@Autowired
	StudentServiceJdbc studentService;

	@Test
	void testRepresentAsTableListOfStudent() {
		List<Student> students = new ArrayList<>();

		students.add(new Student(1, "Pavel", "Pashkin", null));
		students.add(new Student(2, "Piotr", "Vasiuk", 2));
		students.add(new Student(3, "Tasas", "Mirko", 2));
		List<String[]> actual = studentService.representAsTable(students);

		List<String[]> expected = new ArrayList<>();
		expected.add(new String[] { "student_id", "first_name", "last_name", "group_id" });
		expected.add(new String[] { "1", "Pavel", "Pashkin", null });
		expected.add(new String[] { "2", "Piotr", "Vasiuk", "2" });
		expected.add(new String[] { "3", "Tasas", "Mirko", "2" });
		assertEquals(expected.get(2)[2], actual.get(2)[2]);
	}

	@Test
	void testGetGroupsIdsByStudentsCount() {
		
		List<Integer> ids = new ArrayList<>();
		List<Student> students = getStudentsList();
	
		students.stream().forEach(x -> ids.add(x.getId()));
		
		Integer expected = ids.get(1);
		
		when(daoStudents.getGroupSIdCount(any(Integer.class))).thenReturn(ids);
		
		assertEquals(expected, ids.get(1));
	}

	@Test
	void testGetInteger() {
		Student expected = new Student(1, "Pashkin", "Vaskin", 1);
		when(daoStudents.get(expected.getId())).thenReturn(Optional.of(
				new Student(expected.getId(), expected.getFirstName(), expected.getLastName(), expected.getGroupId())));
		Student actual = studentService.getStudent(expected.getId()).get();
		assertEquals(expected, actual);
	}

	@Test
	void testGetListOfInteger() {
		List<Integer> ids = new ArrayList<>();
		List<Student> expected = getStudentsList();
		expected.stream().forEach(x -> ids.add(x.getId()));

		when(daoStudents.get(anyInt())).thenAnswer(invocation -> {
			return expected.stream().filter(x -> x.getId() == invocation.getArgument(0)).findFirst();
		});

		List<Student> actual = studentService.getListOfStudentsByIdsList(ids);

		assertEquals(expected, actual);
	}

	@Test
	void testGetAll() {
		List<Student> expected = getStudentsList();

		when(daoStudents.getAll()).thenReturn(expected);

		List<Student> actual = studentService.getAll();
		assertEquals(expected, actual);
	}

	@Test
	void testSave() {
		List<Student> students = getStudentsList();

		when(daoStudents.save(any(Student.class))).thenAnswer(invocation -> {

			students.add(invocation.getArgument(0));
			return 1;

		});

		Student student = new Student(7, "Niko", "Coursor", 5);

		studentService.saveNewStudent(student);

		assertTrue(students.contains(student));
	}

	@Test
	void testSaveAll() {
		List<Student> expected = getStudentsList();
		List<Student> actual = new ArrayList<>();

		when(daoStudents.save(any(Student.class))).thenAnswer(invocation -> {

			actual.add(invocation.getArgument(0));
			return 1;

		});
		
		studentService.saveAllStudents(expected);
		
		assertEquals(expected, actual);
	}

	@Test
	void testDelete() {
		List<Student> expected = getStudentsList();
		List<Student> actual = getStudentsList();

		when(daoStudents.delete(any(Student.class))).thenAnswer(invocation -> {

			actual.remove(actual.indexOf(invocation.getArgument(0)));
			return 1;

		});
		
		studentService.deleteStudent(actual.get(1));
		
		expected.remove(1);
		
		assertEquals(expected, actual);
	}

	@Test
	void testIsEmpty() {
		List<Student> courses = getStudentsList();

		when(daoStudents.size())
				.thenReturn(courses.size());
		
		assertFalse(studentService.isAnyStudent());
	}

	private List<Student> getStudentsList() {
		List<Student> students = new ArrayList<>();

		students.add(new Student(1, "Pavel", "Pashkin", 1));
		students.add(new Student(2, "Piotr", "Vasiuk", 2));
		students.add(new Student(3, "Tasas", "Mirko", 2));
		students.add(new Student(4, "Tasas", "Zubko", 2));
		students.add(new Student(5, "Sem", "Milek", 3));
		students.add(new Student(6, "Marek", "Rak", 3));
		
		return students;

	}
}
