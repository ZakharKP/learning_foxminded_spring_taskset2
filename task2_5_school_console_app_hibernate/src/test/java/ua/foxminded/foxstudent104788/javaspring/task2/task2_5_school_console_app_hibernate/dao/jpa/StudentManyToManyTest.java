package ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.dao.jpa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.jdbc.Sql;

import ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.dao.impl.DaoCoursesImpl;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.dao.impl.DaoStudentsImpl;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.models.Course;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.models.ForTestsEntitiesCreator;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.models.Group;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.models.Student;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = { DaoStudentsImpl.class,
		DaoCoursesImpl.class }))

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

@Sql(scripts = { "/scripts/table_creation_script.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class StudentManyToManyTest {

	@Autowired
	private DaoStudentsImpl daoStudents;

	@Autowired
	private DaoCoursesImpl daoCourses;

	@BeforeEach
	void beforeEach() {
		for (Course course : ForTestsEntitiesCreator.getNewCourses()) {
			daoCourses.save(course);
		}

		List<Student> students = ForTestsEntitiesCreator.getNewStudents();

		for (Student student : students) {
			daoStudents.save(student);
		}
		
		students = daoStudents.getAll();
		
		addStudentsToCourses(students, daoCourses.getAll());
		for (Student student : students) {
			daoStudents.update(student);
		}
	}

	@Test
	final void testSave() {
		int expected = 1;

		int actual = -1;

		Student student = Student.builder().firstName("John").lastName("Doe").build();

		int status = daoStudents.save(student);
		
		student.getCourses().add(daoCourses.get(1).get());
		student.getCourses().add(daoCourses.get(2).get());

		student.getCourses().stream().forEach(x -> x.getStudents().add(student));

		

		if (status > 0) {
			actual++;
			if (daoCourses.get(2).get().getStudents().contains(student))
				actual++;
		}

		assertEquals(expected, actual);
	}

	@Test
	final void testGet() {
		Student student = daoStudents.get(6).get();

		Course course = daoCourses.get(student.getCourses().stream().findAny().get().getId()).get();

		int expected = 1;
		int actual = -1;

		if (course.getStudents().contains(student))
			actual = 1;

		assertEquals(expected, actual);
	}

	@Test
	final void testDelete() {
		int expected = 1;

		int actual = -1;

		Student student = daoStudents.get(6).get();

		int courseId = student.getCourses().stream().findAny().get().getId();

		for (Course course : student.getCourses()) {
			course.getStudents().remove(student);
		}

		int status = daoStudents.delete(student);

		Course course = daoCourses.get(courseId).get();

		if (status > 0) {
			actual++;
			if (!course.getStudents().contains(student))
				actual++;
			if (daoStudents.size() != 9)
				actual = (int) daoStudents.size();
		}

		assertEquals(expected, actual);
	}

	@Test
	final void testUpdate() {
		int expected = 1;

		int actual = -2;

		Student student = daoStudents.get(6).get();

		int studentId = student.getId();
		int courseId = student.getCourses().stream().findAny().get().getId();

		Course course = daoCourses.get(courseId).get();

		student.getCourses().remove(course);
		course.getStudents().remove(student);

		int status = daoStudents.update(student);

		if (status > 0) {
			actual++;
			if (!daoCourses.get(courseId).get().getStudents().contains(student)) {
				actual++;
				if (!daoStudents.get(studentId).get().getCourses().contains(course))
					actual++;
			}
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
