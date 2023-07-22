
package ua.foxminded.foxstudent104788.javaspring.task2.task2_6_school_console_app_spring_data_jpa.services.testdata;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.jdbc.Sql;

import ua.foxminded.foxstudent104788.javaspring.task2.task2_6_school_console_app_spring_data_jpa.models.Group;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_6_school_console_app_spring_data_jpa.repository.CoursesRepository;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_6_school_console_app_spring_data_jpa.repository.GroupsRepository;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_6_school_console_app_spring_data_jpa.repository.StudentsRepository;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_6_school_console_app_spring_data_jpa.services.TestDataPlaceholder;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
		TestDataPlaceholder.class, GroupsRepository.class, CoursesRepository.class, StudentsRepository.class }))

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

@Sql(scripts = { "/scripts/table_creation_script.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)

@ComponentScan("ua.foxminded.foxstudent104788.javaspring.task2.task2_6_school_console_app_spring_data_jpa.services.impl")
class TestDataPlaceholderTest {

	@Autowired
	private StudentsRepository studentsRepository;

	@Autowired
	private GroupsRepository groupsRepository;

	@Autowired
	private CoursesRepository coursesRepository;

	@Autowired
	private TestDataPlaceholder placeholder;

	@BeforeEach
	void beforeEach() {
		placeholder.generateData();
	}

	@Test
	void testGenerateDataCourses() {

		long excepted = 10;

		long actual = coursesRepository.count();

		assertEquals(excepted, actual);
		assertFalse(coursesRepository.findById(1).get().getStudents().isEmpty());

	}

	@Test
	void testGenerateDataGroups() {
		List<Group> groups = groupsRepository.findAll();

		long excepted = 10;

		long actual = groups.size();

		assertEquals(excepted, actual);

		long groupsWithStudents = groups.stream().filter(x -> !x.getStudents().isEmpty()).count();

		assertTrue(groupsWithStudents > 0);
	}

	@Test
	void testGenerateDataStudents() {
		long excepted = 200;

		long actual = studentsRepository.count();

		assertEquals(excepted, actual);

	}

}
