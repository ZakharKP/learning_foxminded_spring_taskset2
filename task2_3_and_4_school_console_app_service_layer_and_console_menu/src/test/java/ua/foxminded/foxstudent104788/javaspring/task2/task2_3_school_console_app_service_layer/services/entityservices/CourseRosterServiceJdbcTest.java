package ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.services.entityservices;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.dao.DaoCourseRosters;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.dao.DaoCourses;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.dao.DaoStudents;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.dao.impl.DaoCourseRostersJdbc;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.dao.impl.DaoCoursesJdbc;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.dao.impl.DaoStudentsJdbc;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.models.Course;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.models.CourseRoster;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.models.Group;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.models.Student;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.services.entityservices.impl.CourseRosterServiceJdbc;
@SpringBootTest(classes = { CourseRosterServiceJdbc.class })
class CourseRosterServiceJdbcTest {
	@MockBean
	DaoCourseRosters daoCourseRosters;

	@Autowired
	CourseRosterService courseRosterService;
	
	
	@Test
	void testRepresentAsTableListOfCourseRoster() {
		List<CourseRoster> courseRosters = getCourseRostersList();

		List<String[]> actual = courseRosterService.representAsTable(courseRosters);

		List<String[]> expected = new ArrayList<>();
		expected.add(new String[] { "course_roaster_id", "course_id" });
		expected.add(new String[] { "1", "1", "2" });
		expected.add(new String[] { "2", "1", "3" });
		expected.add(new String[] { "3", "3", "2" });
		assertEquals(expected.get(2)[2], actual.get(2)[2]);
	}

	@Test
	void testGetListOfRostersByCourseId() {
		List<CourseRoster> courseRosters = getCourseRostersList();

		when(daoCourseRosters.getListOfRostersByCourseId(any(Integer.class))).thenAnswer(invocation -> {
			List<CourseRoster> actual = new ArrayList<>();
			courseRosters.stream().filter(x -> x.getCourseId() == invocation.getArgument(0)).forEach(x -> actual.add(x));
			return actual;
		});

		int actual = daoCourseRosters.getListOfRostersByCourseId(2).size();

		int expected = 2;

		assertEquals(expected, actual);
	}

	@Test
	void testGetInteger() {
		CourseRoster expected = getCourseRostersList().get(0);
		when(daoCourseRosters.get(expected.getId()))
				.thenReturn(Optional.of(new CourseRoster(expected.getId(), expected.getStudentId(), expected.getCourseId())));
		CourseRoster actual = courseRosterService.getCourseRoster(expected.getId()).get();
		assertEquals(expected, actual);
	}

	@Test
	void testGetListOfInteger() {
		List<Integer> ids = new ArrayList<>();
		List<CourseRoster> expected = getCourseRostersList();
		expected.stream().forEach(x -> ids.add(x.getId()));

		when(daoCourseRosters.get(anyInt())).thenAnswer(invocation -> {
			return expected.stream().filter(x -> x.getId() == invocation.getArgument(0)).findFirst();
		});

		List<CourseRoster> actual = courseRosterService.getListOfCourseRostersByIdsList(ids);

		assertEquals(expected, actual);
	}

	@Test
	void testGetAll() {
		List<CourseRoster> expected = getCourseRostersList();

		when(daoCourseRosters.getAll()).thenReturn(expected);

		List<CourseRoster> actual = courseRosterService.getAll();
		assertEquals(expected, actual);
	}

	@Test
	void testSave() {
		List<CourseRoster> courseRosters = getCourseRostersList();

		when(daoCourseRosters.save(any(CourseRoster.class))).thenAnswer(invocation -> {

			courseRosters.add(invocation.getArgument(0));
			return 1;

		});

		CourseRoster courseRoster = new CourseRoster(5, 2, 2);

		courseRosterService.saveCourseRoster(courseRoster);

		assertTrue(courseRosters.contains(courseRoster));
	}

	@Test
	void testSaveAll() {
		List<CourseRoster> expected = getCourseRostersList();
		List<CourseRoster> actual = new ArrayList<>();

		when(daoCourseRosters.save(any(CourseRoster.class))).thenAnswer(invocation -> {

			actual.add(invocation.getArgument(0));
			return 1;

		});
		
		courseRosterService.saveAll(expected);
		
		assertEquals(expected, actual);
	}

	@Test
	void testDelete() {
		List<CourseRoster> expected = getCourseRostersList();
		List<CourseRoster> actual = getCourseRostersList();

		when(daoCourseRosters.delete(any(CourseRoster.class))).thenAnswer(invocation -> {

			actual.remove(actual.indexOf(invocation.getArgument(0)));
			return 1;

		});
		
		courseRosterService.deleteCourseRoster(actual.get(1));
		
		expected.remove(1);
		
		assertEquals(expected, actual);
	}

	@Test
	void testIsEmpty() {
		List<CourseRoster> courseRosters = getCourseRostersList();

		when(daoCourseRosters.size())
				.thenReturn(courseRosters.size());
		
		assertFalse(courseRosters.isEmpty());
	}

	

	private List<CourseRoster> getCourseRostersList(){
		List<CourseRoster> courseRosters = new ArrayList<>();

		courseRosters.add(new CourseRoster(1, 1, 2));
		courseRosters.add(new CourseRoster(2, 1, 3));
		courseRosters.add(new CourseRoster(3, 3, 2));
		
		return courseRosters;
	}
}
