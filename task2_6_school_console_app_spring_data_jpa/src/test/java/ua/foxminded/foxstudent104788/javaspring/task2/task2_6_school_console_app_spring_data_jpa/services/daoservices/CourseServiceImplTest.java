
package ua.foxminded.foxstudent104788.javaspring.task2.task2_6_school_console_app_spring_data_jpa.services.daoservices;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
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
import ua.foxminded.foxstudent104788.javaspring.task2.task2_6_school_console_app_spring_data_jpa.repository.CoursesRepository;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_6_school_console_app_spring_data_jpa.services.impl.CourseServiceImpl;

@SpringBootTest(classes = { CourseServiceImpl.class })
class CourseServiceImplTest {

	@MockBean
	CoursesRepository coursesRepository;

	@Autowired
	CourseServiceImpl courseService;

	@Test
	final void testRepresentAsTable() {
		List<Course> courses = getCourses();

		List<String[]> actual = courseService.representAsTable(courses);

		List<String[]> expected = new ArrayList<>();
		expected.add(new String[] { Constants.COURSE_COLUMN_NAME_ID, Constants.COURSE_COLUMN_NAME_NAME,
				Constants.COURSE_COLUMN_NAME_DESCRIPTION, "Student's amount" });
		expected.add(new String[] { "1", "biology", "anatomia Course", "2" });
		expected.add(new String[] { "2", "math", "math Course", "3" });
		expected.add(new String[] { "3", "art", "art Course", "5" });
		assertEquals(expected.get(2)[2], actual.get(2)[2]);
	}

	@Test
	final void testGetCourseByName() {
		List<Course> courses = getCourses();

		Course expected = courses.get(1);

		when(coursesRepository.findByCourseNameContaining(any(String.class))).thenAnswer(invocation -> {
			return courses.stream().filter(x -> x.getCourseName().equals(invocation.getArgument(0))).findFirst();
		});

		Course actual = courseService.getCourseByName(expected.getCourseName()).get();

		assertEquals(expected, actual);

	}

	@Test
	final void testGetCourse() {
		Course expected = getCourses().get(1);
		when(coursesRepository.findById(expected.getId())).thenReturn(Optional.of(new Course(expected.getId(),
				expected.getCourseName(), expected.getCourseDescription(), expected.getStudents())));
		Course actual = courseService.getCourse(expected.getId()).get();

		assertEquals(expected, actual);
	}

	@Test
	final void testGetListOfCoursesByIdsList() {
		List<Integer> ids = new ArrayList<>();
		List<Course> expected = getCourses();
		expected.stream().forEach(x -> ids.add(x.getId()));

		when(coursesRepository.findById(anyInt())).thenAnswer(invocation -> {
			return expected.stream().filter(x -> x.getId() == invocation.getArgument(0)).findFirst();
		});

		List<Course> actual = courseService.getListOfCoursesByIdsList(ids);

		assertEquals(expected, actual);

	}

	@Test
	final void testGetAll() {
		List<Course> expected = getCourses();

		when(coursesRepository.findAll()).thenReturn(expected);

		List<Course> actual = courseService.getAll();
		assertEquals(expected, actual);
	}

	@Test
	final void testSaveCourse() {
		List<Course> courses = getCourses();

		when(coursesRepository.save(any(Course.class))).thenAnswer(invocation -> {

			courses.add(invocation.getArgument(0));
			return invocation.getArgument(0);

		});

		Course course = ForTestsEntitiesCreator.getNewCourse();

		courseService.saveCourse(course);

		assertTrue(courses.contains(course));

	}

	@Test
	final void testSaveAllCourses() {
		List<Course> expected = getCourses();
		List<Course> actual = new ArrayList<>();

		when(coursesRepository.saveAll(anyList())).thenAnswer(invocation -> {

			actual.addAll(invocation.getArgument(0));
			return actual;

		});

		courseService.saveAllCourses(expected);

		assertEquals(expected, actual);
	}

	@Test
	final void testDeleteCourse() {
		List<Course> expected = getCourses();
		List<Course> actual = getCourses();

		Mockito.doAnswer(invocation -> {

			actual.remove(actual.indexOf(invocation.getArgument(0)));
			return null;

		}).when(coursesRepository).delete(any(Course.class));

		courseService.deleteCourse(actual.get(1));

		expected.remove(1);

		assertEquals(expected, actual);

	}

	@Test
	final void testIsEmpty() {
		List<Course> courses = getCourses();

		when(coursesRepository.count()).thenReturn((long) courses.size());

		assertFalse(courseService.isEmpty());
	}

	private List<Course> getCourses() {
		List<Course> courses = ForTestsEntitiesCreator.getCourses();

		List<Student> students = getStudents();

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

		return courses;
	}

	private List<Student> getStudents() {
		List<Student> students = ForTestsEntitiesCreator.getStudents();

		List<Group> groups = ForTestsEntitiesCreator.getGroups();

		for (int i = 0; i < students.size(); i++) {
			if (i < 2) {
				Group group = groups.get(0);
				students.get(i).setGroup(group);
			}
			if (i < 5 && i >= 2) {
				Group group = groups.get(1);
				students.get(i).setGroup(group);
			}
			if (i >= 5) {
				Group group = groups.get(2);
				students.get(i).setGroup(group);
			}
		}

		return students;
	}
}
