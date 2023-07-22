package ua.foxminded.foxstudent104788.javaspring.task2.task2_6_school_console_app_spring_data_jpa.services.daoservices;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import ua.foxminded.foxstudent104788.javaspring.task2.task2_6_school_console_app_spring_data_jpa.models.Constants;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_6_school_console_app_spring_data_jpa.models.Course;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_6_school_console_app_spring_data_jpa.models.ForTestsEntitiesCreator;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_6_school_console_app_spring_data_jpa.models.Group;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_6_school_console_app_spring_data_jpa.models.Student;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_6_school_console_app_spring_data_jpa.repository.StudentsRepository;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_6_school_console_app_spring_data_jpa.services.impl.StudentServiceImpl;

@SpringBootTest(classes = { StudentServiceImpl.class })
class StudentServiceImplTest {

	@MockBean
	StudentsRepository studentsRepository;

	@Autowired
	StudentServiceImpl studentService;

	@Test
	final void testRepresentAsTable() {
		List<Student> students = getStudents();

		List<String[]> actual = studentService.representAsTable(students);

		List<String[]> expected = new ArrayList<>();
		expected.add(new String[] { Constants.STUDENT_COLUMN_NAME_ID, Constants.STUDENT_COLUMN_NAME_FIRST_NAME,
				Constants.STUDENT_COLUMN_NAME_LAST_NAME, Constants.GROUP_COLUMN_NAME_ID, "courses amount" });
		expected.add(new String[] { "1", "Pavel", "Pashkin", null, "2" });
		expected.add(new String[] { "2", "Piotr", "Vasiuk", "2", "3" });
		expected.add(new String[] { "3", "Tasas", "Mirko", "2", "5" });
		expected.add(new String[] { "4", "Tasas", "Zubko", "2", "5" });
		expected.add(new String[] { "5", "Sem", "Milek", "2", "5" });
		expected.add(new String[] { "6", "Marek", "Rak", "2", "5" });
		expected.add(new String[] { "7", "John", "Doe", "2", "5" });
		expected.add(new String[] { "8", "Nikolos", "Flamel", "2", "5" });
		expected.add(new String[] { "9", "Bernard", "Verber", "2", "5" });
		expected.add(new String[] { "10", "John", "Snow", "2", "5" });
		assertEquals(expected.get(2)[2], actual.get(2)[2]);
	}

	@Test
	final void testGetStudent() {
		Student expected = getStudents().get(0);
		when(studentsRepository.findById(expected.getId())).thenReturn(Optional.of(new Student(expected.getId(),
				expected.getFirstName(), expected.getLastName(), expected.getGroup(), expected.getCourses())));
		Student actual = studentService.getStudent(expected.getId()).get();
		assertEquals(expected, actual);
	}

	@Test
	final void testGetListOfStudentsByIdsList() {
		List<Integer> ids = new ArrayList<>();
		List<Student> expected = getStudents();
		expected.stream().forEach(x -> ids.add(x.getId()));

		when(studentsRepository.findAllById(anyList())).thenAnswer(invocation -> {
			List<Student> answer = new ArrayList<>();
			List<Integer> askIds = invocation.getArgument(0);
			
			for(Integer id : askIds) {
				expected.stream().filter(x -> x.getId() == id).forEach(answer :: add);
			}
			
			return answer;
		});

		List<Student> actual = studentService.getListOfStudentsByIdsList(ids);

		assertEquals(expected, actual);

	}

	@Test
	final void testGetAll() {
		List<Student> expected = getStudents();

		when(studentsRepository.findAll()).thenReturn(expected);

		List<Student> actual = studentService.getAll();
		assertEquals(expected, actual);

	}

	@Test
	final void testSaveNewStudent() {
		List<Student> students = getStudents();

		when(studentsRepository.save(any(Student.class))).thenAnswer(invocation -> {

			students.add(invocation.getArgument(0));
			return invocation.getArgument(0);

		});

		Student student = ForTestsEntitiesCreator.getNewStudent();

		studentService.saveNewStudent(student);

		assertTrue(students.contains(student));
	}

	@Test
	final void testSaveAllStudents() {
		List<Student> expected = getStudents();
		List<Student> actual = new ArrayList<>();

		when(studentsRepository.saveAll(anyList())).thenAnswer(invocation -> {

			actual.addAll(invocation.getArgument(0));
			return actual;

		});

		studentService.saveAllStudents(expected);

		assertEquals(expected, actual);
	}

	@Test
	final void testDeleteStudent() {
		List<Student> expected = getStudents();
		List<Student> actual = getStudents();

		Mockito.doAnswer(invocation -> {

			actual.remove(actual.indexOf(invocation.getArgument(0)));
			return null;

		}).when(studentsRepository).delete(any(Student.class));

		when(studentsRepository.save(any(Student.class))).thenAnswer(invocation -> {
			return invocation.getArgument(0);

		});
		
		studentService.deleteStudent(actual.get(1));

		expected.remove(1);

		assertEquals(expected, actual);
	}

	@Test
	void testIsEmpty() {
		List<Student> courses = getStudents();

		when(studentsRepository.count()).thenReturn((long) courses.size());

		assertFalse(studentService.isEmpty());
	}

	private List<Student> getStudents() {
		List<Student> students = ForTestsEntitiesCreator.getStudents();

		List<Group> groups = ForTestsEntitiesCreator.getGroups();
		List<Course> courses = ForTestsEntitiesCreator.getCourses();

		for (int i = 0; i < students.size(); i++) {
			if (i < 2) {
				Course course = courses.get(0);
				students.get(i).getCourses().add(course);
			}
			if (i < 5 && i >= 2) {
				Group group = groups.get(1);
				Course course = courses.get(1);
				students.get(i).setGroup(group);
				students.get(i).getCourses().add(course);
			}
			if (i >= 5) {
				Group group = groups.get(2);
				students.get(i).setGroup(group);
				Course course = courses.get(2);
				students.get(i).getCourses().add(course);
			}
		}

		return students;
	}

	

}
