package ua.foxminded.foxstudent104788.javaspring.task2.task2_2_school_console_app_Spring_Boot_JDBC_Api.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import ua.foxminded.foxstudent104788.javaspring.task2.task2_2_school_console_app_Spring_Boot_JDBC_Api.dao.impl.DaoGroupsJdbc;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_2_school_console_app_Spring_Boot_JDBC_Api.models.Group;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = { "/scripts/table_creation_script.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class DaoGroupsImplTest {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	private DaoGroupsJdbc daoGroups;

	@BeforeEach
	void beforeAll() {
		daoGroups = new DaoGroupsJdbc(jdbcTemplate);
	}
	
	@Test
	void testSaveGroup() {
		Group group = new Group(null, "xx-11");

		int actual = daoGroups.save(group);

		assertTrue(actual > 0);
	}

	@Test
	void testGetInteger() {
		List<Group> groups = new ArrayList<>();

		groups.add(new Group(1, "xx-11"));
		groups.add(new Group(2, "xy-15"));
		groups.add(new Group(3, "xz-16"));

		for (Group group : groups) {
			daoGroups.save(group);
		}
		
		Group actual = daoGroups.get(3).get();

		String expected = "xz-16";

		assertEquals(expected, actual.getGroupName());
	}

	@Test
	void testGetListOfInteger() {
		List<Group> groups = new ArrayList<>();

		groups.add(new Group(1, "xx-11"));
		groups.add(new Group(2, "xy-15"));
		groups.add(new Group(3, "xz-16"));

		for (Group group : groups) {
			daoGroups.save(group);
		}

		List<Integer> ids = new ArrayList<>();
		ids.add(1);
		ids.add(2);
		ids.add(3);

		List<Group> actual = daoGroups.get(ids);

		String expected = "xz-16";

		assertEquals(expected, actual.get(2).getGroupName());
	}

	@Test
	void testGetAll() {
		List<Group> groups = new ArrayList<>();

		groups.add(new Group(1, "xx-11"));
		groups.add(new Group(2, "xy-15"));
		groups.add(new Group(3, "xz-16"));

		for (Group group : groups) {
			daoGroups.save(group);
		}
		
		List<Group> actual = daoGroups.getAll();

		Integer expected = 3;

		assertEquals(expected, actual.size());
	}

	@Test
	void testDelete() {
		List<Group> groups = new ArrayList<>();

		groups.add(new Group(1, "xx-11"));
		groups.add(new Group(2, "xy-15"));
		groups.add(new Group(3, "xz-16"));

		for (Group group : groups) {
			daoGroups.save(group);
		}
		
		daoGroups.delete(new Group(2, null));
		
		List<Group> actual = daoGroups.getAll();

		Integer expected = 2;

		assertEquals(expected, actual.size());
	}

	@Test
	void testGetTable() {
		List<Group> groups = new ArrayList<>();

		groups.add(new Group(null, "xx-11"));
		groups.add(new Group(2, "xy-15"));
		groups.add(new Group(3, "xz-16"));

		List<String[]> actual = daoGroups.representAsTable(groups);

		List<String[]> expected = new ArrayList<>();
		expected.add(new String[] { "group_id", "group_name" });
		expected.add(new String[] { "1", "xx-11" });
		expected.add(new String[] { "2", "xy-15" });
		expected.add(new String[] { "3", "xz-16" });
		assertEquals(expected.get(2)[1], actual.get(2)[1]);
	}

	
}
