package ua.foxminded.foxstudent104788.javaspring.task2.task2_6_school_console_app_spring_data_jpa.services.viewservices;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ua.foxminded.foxstudent104788.javaspring.task2.task2_6_school_console_app_spring_data_jpa.services.PathStorage;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_6_school_console_app_spring_data_jpa.services.impl.PathStorageImpl;

class PathStorageImplTest {

	PathStorage pathStorage;

	@BeforeEach
	void setStorage() {
		pathStorage = new PathStorageImpl();
	}

	@Test
	void testGetMenuPath() {

		Path pathDir = Paths.get("src", "main", "resources");

		Path actual = pathStorage.getMenuPath();

		Path expected = Paths.get("src", "main", "resources", "settings", "menu.txt");

		assertEquals(expected, actual);
	}

	@Test
	void testGetMessagesPath() {
		Path pathDir = Paths.get("src", "main", "resources");

		Path actual = pathStorage.getMessagesPath();

		Path expected = Paths.get("src", "main", "resources", "settings", "messages.properties");

		assertEquals(expected, actual);
	}

	@Test
	void testGetCreateTableScriptPath() {
		Path pathDir = Paths.get("src", "main", "resources");

		Path actual = pathStorage.getCreateTableScriptPath();

		Path expected = Paths.get("src", "main", "resources", "dao", "scripts", "table_creation_script.sql");

		assertEquals(expected, actual);
	}

}
