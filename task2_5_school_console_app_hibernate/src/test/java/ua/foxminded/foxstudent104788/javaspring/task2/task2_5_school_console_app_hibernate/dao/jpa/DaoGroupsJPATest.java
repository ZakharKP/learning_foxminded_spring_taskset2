package ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.dao.jpa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.jdbc.Sql;

import ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.dao.impl.DaoGroupsImpl;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.models.ForTestsEntitiesCreator;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.models.Group;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
		DaoGroupsImpl.class }))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = { "/scripts/table_creation_script.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class DaoGroupsJPATest {

	@Autowired
	private DaoGroupsImpl daoGroups;

	@Test
	void testSave() {
		Group group = ForTestsEntitiesCreator.getNewGroup();

		int actual = daoGroups.save(group);

		assertTrue(actual > 0);
	}

	@Test
	void testGetInteger() {
		List<Group> groups = ForTestsEntitiesCreator.getNewGroups();

		for (Group group : groups) {
			daoGroups.save(group);
		}

		Group actual = daoGroups.get( 3).get();

		String expected = "xz-16";

		assertEquals(expected, actual.getGroupName());

	}

	@Test
	void testGetAll() {
		List<Group> groups = ForTestsEntitiesCreator.getNewGroups();
		List<Group> expected = ForTestsEntitiesCreator.getGroups();

		for (Group group : groups) {
			daoGroups.save(group);
		}

		List<Group> actual = daoGroups.getAll();

		assertEquals(expected, actual);
	}

	@Test
	void testDelete() {
		List<Group> groups = ForTestsEntitiesCreator.getNewGroups();
		List<Group> expected = ForTestsEntitiesCreator.getGroups();

		for (Group group : groups) {
			daoGroups.save(group);
		}

		int status = daoGroups.delete(groups.get(1));

		if (status > 0) {
			expected.remove(1);
		}

		List<Group> actual = daoGroups.getAll();

		assertEquals(expected, actual);
	}

	@Test
	void Update() {
		List<Group> groups = ForTestsEntitiesCreator.getNewGroups();

		for (Group group : groups) {
			daoGroups.save(group);
		}

		Group expected = ForTestsEntitiesCreator.getGroups().get(1);
		expected.setGroupName("YY-77");

		int status = daoGroups.update(expected);

		Group actual = null;

		if (status > 0) {
			actual = daoGroups.get(expected.getId()).get();
		}

		assertEquals(expected, actual);
	}

	@Test
	void testSize() {

		List<Group> groups = ForTestsEntitiesCreator.getNewGroups();

		for (Group group : groups) {
			daoGroups.save(group);
		}

		long actual = daoGroups.size();

		assertEquals(groups.size(), actual);
	}

	

	

}
