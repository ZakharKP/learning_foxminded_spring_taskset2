
package ua.foxminded.foxstudent104788.javaspring.task2.task2_6_school_console_app_spring_data_jpa.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.jdbc.Sql;

import ua.foxminded.foxstudent104788.javaspring.task2.task2_6_school_console_app_spring_data_jpa.models.Course;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_6_school_console_app_spring_data_jpa.models.ForTestsEntitiesCreator;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_6_school_console_app_spring_data_jpa.models.Student;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_6_school_console_app_spring_data_jpa.repository.CoursesRepository;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_6_school_console_app_spring_data_jpa.repository.StudentsRepository;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = { StudentsRepository.class,
		CoursesRepository.class }))

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

@Sql(scripts = { "/scripts/table_creation_script.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class StudentManyToManyTest {

	@Autowired
	private StudentsRepository studentsRepository;

	@Autowired
	private CoursesRepository coursesRepository;

	@BeforeEach
	void beforeEach() {
		for (Course course : ForTestsEntitiesCreator.getNewCourses()) {
			coursesRepository.save(course);
		}

		List<Student> students = ForTestsEntitiesCreator.getNewStudents();

		for (Student student : students) {
			studentsRepository.save(student);
		}

		students = studentsRepository.findAll();

		addStudentsToCourses(students, coursesRepository.findAll());
		for (Student student : students) {
			studentsRepository.save(student);
		}
	}

	@Test
	final void testSave() {
		int expected = 1;

		int actual = 0;

		Student student = Student.builder().firstName("John").lastName("Doe").build();

		studentsRepository.save(student);

		student.getCourses().add(coursesRepository.findById(1).get());
		student.getCourses().add(coursesRepository.findById(2).get());

		student.getCourses().stream().forEach(x -> x.getStudents().add(student));

		if (coursesRepository.findById(2).get().getStudents().contains(student))
			actual++;

		assertEquals(expected, actual);
	}

	@Test
	final void testGet() {
		Student student = studentsRepository.findById(6).get();

		Course course = coursesRepository.findById(student.getCourses().stream().findAny().get().getId()).get();

		int expected = 1;
		int actual = -1;

		if (course.getStudents().contains(student))
			actual = 1;

		assertEquals(expected, actual);
	}

	@Test
	final void testDelete() {
		long expected = 1;

		long actual = 0;

		Student student = studentsRepository.findById(6).get();

		int courseId = student.getCourses().stream().findAny().get().getId();

		for (Course course : student.getCourses()) {
			course.getStudents().remove(student);
		}

		studentsRepository.delete(student);

		Course course = coursesRepository.findById(courseId).get();

		if (!course.getStudents().contains(student)) {
			actual++;
			if (studentsRepository.count() != 9)
				actual = studentsRepository.count();
		}

		assertEquals(expected, actual);
	}

	@Test
	final void testUpdate() {
		int expected = 1;

		int actual = -1;

		Student student = studentsRepository.findById(6).get();

		int studentId = student.getId();
		int courseId = student.getCourses().stream().findAny().get().getId();

		Course course = coursesRepository.findById(courseId).get();

		student.getCourses().remove(course);
		course.getStudents().remove(student);

		studentsRepository.save(student);

			if (!coursesRepository.findById(courseId).get().getStudents().contains(student)) {
				actual++;
				if (!studentsRepository.findById(studentId).get().getCourses().contains(course))
					actual++;
			}
		

		assertEquals(expected, actual);
	}

	private void addStudentsToCourses(List<Student> students, List<Course> courses) {
		for (int i = 0; i < students.size(); i++) {
			if (i < 2) {
				Course course = courses.get(0);
				students.get(i).getCourses().add(course);
				course.getStudents().add(students.get(i));
			}
			if (i < 5 && i >= 2) {
				Course course = courses.get(1);
				students.get(i).getCourses().add(course);
				course.getStudents().add(students.get(i));
			}
			if (i >= 5) {
				Course course = courses.get(2);
				students.get(i).getCourses().add(course);
				course.getStudents().add(students.get(i));
			}
		}

	}

}
