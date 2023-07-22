package ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.services.testdata;

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

import ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.dao.impl.DaoCoursesImpl;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.dao.impl.DaoGroupsImpl;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.dao.impl.DaoStudentsImpl;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.models.Group;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.services.TestDataPlaceholder;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
		TestDataPlaceholder.class, DaoGroupsImpl.class, DaoCoursesImpl.class, DaoStudentsImpl.class }))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = { "/scripts/table_creation_script.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@ComponentScan("ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.services.daoservices")
class TestDataPlaceholderTest {

	@Autowired
	private DaoStudentsImpl daoStudents;

	@Autowired
	private DaoGroupsImpl daoGroups;

	@Autowired
	private DaoCoursesImpl daoCourses;

	@Autowired
	private TestDataPlaceholder placeholder;

	@BeforeEach
	void beforeEach() {
		placeholder.generateData();
	}

	@Test
	void testGenerateDataCourses() {

		long excepted = 10;

		long actual = daoCourses.size();

		assertEquals(excepted, actual);
		assertFalse(daoCourses.get(1).get().getStudents().isEmpty());

	}

	@Test
	void testGenerateDataGroups() {
		List<Group> groups = daoGroups.getAll();

		long excepted = 10;

		long actual = groups.size();

		assertEquals(excepted, actual);

		long groupsWithStudents = groups.stream().filter(x -> !x.getStudents().isEmpty()).count();

		assertTrue(groupsWithStudents > 0);
	}

	@Test
	void testGenerateDataStudents() {
		long excepted = 200;

		long actual = daoStudents.size();

		assertEquals(excepted, actual);

	}

}
