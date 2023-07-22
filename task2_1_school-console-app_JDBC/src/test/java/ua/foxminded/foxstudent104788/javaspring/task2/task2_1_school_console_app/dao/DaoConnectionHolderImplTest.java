package ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;

import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.dao.DaoConnectionHolder;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.dao.impl.DaoConnectionHolderImpl;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.models.DaoConnectionSettings;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.services.PathStorage;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.services.ResourceLoader;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.services.impl.PathStorageImpl;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.services.impl.ResourceLoaderImpl;

class DaoConnectionHolderImplTest {

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
	void testUpdateExecuteMapOfStringObject() {
		PathStorage pathStorage = new PathStorageImpl(Paths.get("src", "main", "resourses"));

		connection.executeScript(pathStorage.getCreateTableScriptPath());
		Map<Object[], String> statement = new HashMap<>();

		statement.put(new Object[] { "Petr", "Sirotkin" },
				"INSERT INTO students (first_name, last_name) VALUES (?, ?)");
		statement.put(new Object[] { "Petruha", "Sirotkino", 1 },
				"INSERT INTO students (first_name, last_name, group_id) VALUES (?, ?, ?)");

		int actual = connection.updateExecute(
				"INSERT INTO students (student_id, first_name, last_name) VALUES (?, ?, ?)", 1, "Petr", "Sirotkin");
		int expected = 1;

		assertEquals(expected, actual);

	}

	@Test
	void testUpdateExecuteStringObjectArray() {
		PathStorage pathStorage = new PathStorageImpl(Paths.get("src", "main", "resourses"));

		connection.executeScript(pathStorage.getCreateTableScriptPath());

		int actual = connection.updateExecute(
				"INSERT INTO students (student_id, first_name, last_name) VALUES (?, ?, ?)", 1, "Petr", "Sirotkin");
		int expected = 1;

		assertEquals(expected, actual);
	}

	@Test
	void testSelectExecuteStringObjectArray() {
		PathStorage pathStorage = new PathStorageImpl(Paths.get("src", "main", "resourses"));

		connection.executeScript(pathStorage.getCreateTableScriptPath());
		Map<Object[], String> statement = new HashMap<>();
		connection.updateExecute("INSERT INTO groups (group_name) VALUES(?)", "xx-15");
		statement.put(new Object[] { "Petr", "Sirotkin" },
				"INSERT INTO students (first_name, last_name) VALUES (?, ?)");
		statement.put(new Object[] { "Petruha", "Sirotkino", 1 },
				"INSERT INTO students (first_name, last_name, group_id) VALUES (?, ?, ?)");
		connection.updateExecute(statement);

		int actual = connection.selectExecute("SELECT group_id, first_name FROM students ").size();
		int expected = 3;
		assertEquals(expected, actual);
	}

	@Test
	void testSelectExecuteStringObjectArray_withParam() {
		PathStorage pathStorage = new PathStorageImpl(Paths.get("src", "main", "resourses"));

		connection.executeScript(pathStorage.getCreateTableScriptPath());
		Map<Object[], String> statement = new HashMap<>();
		connection.updateExecute("INSERT INTO groups (group_name) VALUES(?)", "xx-15");
		statement.put(new Object[] { "Petr", "Sirotkin" },
				"INSERT INTO students (first_name, last_name) VALUES (?, ?)");
		statement.put(new Object[] { "Petruha", "Sirotkino", 1 },
				"INSERT INTO students (first_name, last_name, group_id) VALUES (?, ?, ?)");
		connection.updateExecute(statement);

		String actual = connection.selectExecute("SELECT first_name FROM students WHERE group_id = ? ", 1).get(1)
				.get("first_name");
		String expected = "Petruha";
		assertEquals(expected, actual);
	}

	@Test
	void testSelectExecuteStringListOfObject() {
		PathStorage pathStorage = new PathStorageImpl(Paths.get("src", "main", "resourses"));

		connection.executeScript(pathStorage.getCreateTableScriptPath());
		Map<Object[], String> statement = new HashMap<>();
		connection.updateExecute("INSERT INTO groups (group_name) VALUES(?)", "xx-15");
		connection.updateExecute("INSERT INTO groups (group_name) VALUES(?)", "xy-15");
		statement.put(new Object[] { "Petr", "Sirotkin", 2 },
				"INSERT INTO students (first_name, last_name, group_id) VALUES (?, ?, ?)");
		statement.put(new Object[] { "Petruha", "Sirotkino", 1 },
				"INSERT INTO students (first_name, last_name, group_id) VALUES (?, ?, ?)");

		connection.updateExecute(statement);

		List<Object[]> parametres = new ArrayList<>();
		parametres.add(new Object[] { 1 });
		parametres.add(new Object[] { 2 });

		String actual = connection.selectExecute("SELECT first_name " + "FROM students WHERE group_id = ? ", parametres)
				.get(1).get("first_name");
		String expected = "Petruha";
		assertEquals(expected, actual);
	}

}
