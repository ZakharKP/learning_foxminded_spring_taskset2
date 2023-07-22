package ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.services.entityservices;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
import ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.models.Course;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.services.entityservices.impl.CourseServiceJdbc;

@SpringBootTest(classes = { CourseServiceJdbc.class })
class CourseServiceJdbcTest {
	@MockBean
	DaoCourses daoCourses;

	@Autowired
	CourseServiceJdbc courseServiceJdbc;
	

	@Test
	void testRepresentAsTableListOfCourse() {
		List<Course> courses = new ArrayList<>();

		courses.add(new Course(1, "biology", "anatomia Course"));
		courses.add(new Course(2, "math", "math Course"));
		courses.add(new Course(3, "art", "art Course"));
		List<String[]> actual = courseServiceJdbc.representAsTable(courses);

		List<String[]> expected = new ArrayList<>();
		expected.add(new String[] { "course_id", "course_name", "course_description" });
		expected.add(new String[] { "1", "biology", "anatomia Course" });
		expected.add(new String[] { "2", "math", "math Course" });
		expected.add(new String[] { "3", "art", "art Course" });
		assertEquals(expected.get(2)[2], actual.get(2)[2]);
	}

	@Test
	void testGetByName() {
		List<Course> courses = getCoursesList();
		
		Course expected = courses.get(1);

		when(daoCourses.getByName(any(String.class))).thenAnswer(invocation -> {
			return courses.stream().filter(x -> x.getCourseName().equals(invocation.getArgument(0))).findFirst();
		});
		
		Course actual = courseServiceJdbc.getCourseByName(expected.getCourseName()).get();
		
		assertEquals(expected, actual);
	}

	@Test
	void testGetInteger() {
		Course expected = getCourse();
		when(daoCourses.get(expected.getId()))
				.thenReturn(Optional.of(new Course(expected.getId(), expected.getCourseName(), expected.getCourseDescription())));
		Course actual = courseServiceJdbc.getCourse(expected.getId()).get();
		assertEquals(expected, actual);
	}

	@Test
	void testGetListOfInteger() {
		List<Integer> ids = new ArrayList<>();
		List<Course> expected = getCoursesList();
		expected.stream().forEach(x -> ids.add(x.getId()));

		when(daoCourses.get(anyInt())).thenAnswer(invocation -> {
			return expected.stream().filter(x -> x.getId() == invocation.getArgument(0)).findFirst();
		});

		List<Course> actual = courseServiceJdbc.getListOfCoursesByIdsList(ids);

		assertEquals(expected, actual);

	}

	@Test
	void testGetAll() {
		List<Course> expected = getCoursesList();

		when(daoCourses.getAll()).thenReturn(expected);

		List<Course> actual = courseServiceJdbc.getAll();
		assertEquals(expected, actual);
	}

	@Test
	void testSave() {
		List<Course> courses = getCoursesList();

		when(daoCourses.save(any(Course.class))).thenAnswer(invocation -> {

			courses.add(invocation.getArgument(0));
			return 1;

		});

		Course course = new Course(4, "math", "math Course");

		courseServiceJdbc.saveCourse(course);

		assertTrue(courses.contains(course));
	}

	@Test
	void testSaveAll() {
		List<Course> expected = getCoursesList();
		List<Course> actual = new ArrayList<>();

		when(daoCourses.save(any(Course.class))).thenAnswer(invocation -> {

			actual.add(invocation.getArgument(0));
			return 1;

		});
		
		courseServiceJdbc.saveAllCourses(expected);
		
		assertEquals(expected, actual);
	}

	@Test
	void testDelete() {
		List<Course> expected = getCoursesList();
		List<Course> actual = getCoursesList();

		when(daoCourses.delete(any(Course.class))).thenAnswer(invocation -> {

			actual.remove(actual.indexOf(invocation.getArgument(0)));
			return 1;

		});
		
		courseServiceJdbc.deleteCourse(actual.get(1));
		
		expected.remove(1);
		
		assertEquals(expected, actual);
	}

	@Test
	void testIsEmpty() {
		List<Course> courses = getCoursesList();

		when(daoCourses.size())
				.thenReturn(courses.size());
		
		assertFalse(courseServiceJdbc.isAnyCourses());
	}

	private List<Course> getCoursesList(){
		List<Course> courses = new ArrayList<>();

		courses.add(new Course(1, "biology", "anatomia Course"));
		courses.add(new Course(2, "math", "math Course"));
		courses.add(new Course(3, "art", "art Course"));
		
		return courses;
	}
	
	private Course getCourse() {
		return getCoursesList().get(0);
	}

}
