package ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.dao.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;

import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.dao.DaoConnectionHolder;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.dao.DaoGroups;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.dao.impl.DaoConnectionHolderImpl;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.dao.impl.DaoGroupsImpl;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.models.DaoConnectionSettings;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.models.Group;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.services.PathStorage;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.services.ResourceLoader;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.services.impl.PathStorageImpl;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.services.impl.ResourceLoaderImpl;

class DaoGroupsImplTest {

	static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine");

	DaoConnectionHolder connection;

	@BeforeAll
	static void beforeAll() {
		postgres.start();
	}

	@AfterAll
	static void afterAll() {
		postgres.stop();
	}

	@BeforeEach
	void setting() {
		DaoConnectionSettings daoConnectionSettings = new DaoConnectionSettings(postgres.getJdbcUrl(),
				postgres.getUsername(), postgres.getPassword());
		connection = new DaoConnectionHolderImpl(daoConnectionSettings);
	}

	@Test
	void testAddGroup() {
		PathStorage pathStorage = new PathStorageImpl(Paths.get("src", "main", "resourses"));

		connection.executeScript(pathStorage.getCreateTableScriptPath());

		DaoGroups daoGroups = new DaoGroupsImpl(connection);

		Group group = new Group(null, "xx-11");

		int actual = daoGroups.addGroup(group);

		assertTrue(actual > 0);
	}

	@Test
	void testAddGroups() {
		PathStorage pathStorage = new PathStorageImpl(Paths.get("src", "main", "resourses"));
		ResourceLoader loader = new ResourceLoaderImpl(pathStorage);

		DaoConnectionSettings daoConnectionSettings = loader.getDaoConnectionSettings();

		connection.executeScript(pathStorage.getCreateTableScriptPath());

		DaoGroups daoGroups = new DaoGroupsImpl(connection);

		List<Group> groups = new ArrayList<>();

		groups.add(new Group(null, "xx-11"));
		groups.add(new Group(null, "xy-15"));
		groups.add(new Group(null, "xz-16"));

		int actual = daoGroups.addGroups(groups);

		assertTrue(actual > 0);
	}

	@Test
	void testGetTable() {
		List<Group> groups = new ArrayList<>();

		groups.add(new Group(null, "xx-11"));
		groups.add(new Group(2, "xy-15"));
		groups.add(new Group(3, "xz-16"));

		List<String[]> actual = new DaoGroupsImpl(null).getTable(groups);

		List<String[]> expected = new ArrayList<>();
		expected.add(new String[] { "group_id", "group_name" });
		expected.add(new String[] { "1", "xx-11" });
		expected.add(new String[] { "2", "xy-15" });
		expected.add(new String[] { "3", "xz-16" });
		assertEquals(expected.get(2)[1], actual.get(2)[1]);
	}

	@Test
	void testGetById() {
		PathStorage pathStorage = new PathStorageImpl(Paths.get("src", "main", "resourses"));
		ResourceLoader loader = new ResourceLoaderImpl(pathStorage);

		DaoConnectionSettings daoConnectionSettings = loader.getDaoConnectionSettings();

		connection.executeScript(pathStorage.getCreateTableScriptPath());

		DaoGroups daoGroups = new DaoGroupsImpl(connection);

		List<Group> groups = new ArrayList<>();

		groups.add(new Group(1, "xx-11"));
		groups.add(new Group(2, "xy-15"));
		groups.add(new Group(3, "xz-16"));

		daoGroups.addGroups(groups);

		List<Integer> ids = new ArrayList<>();
		ids.add(1);
		ids.add(2);
		ids.add(3);

		List<Group> actual = daoGroups.getById(ids);

		String expected = "xz-16";

		assertEquals(expected, actual.get(2).getGroupName());
	}
}
