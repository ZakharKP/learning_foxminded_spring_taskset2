package ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.services.impl.PathStorageImpl;

class PathStorageImplTest {

	@Test
	void testGetMenuPath() {

		Path pathDir = Paths.get("src", "main", "resourses");

		Path actual = new PathStorageImpl(pathDir).getMenuPath();

		Path expected = Paths.get("src", "main", "resourses", "settings", "menu.txt");

		assertEquals(expected, actual);
	}

	@Test
	void testGetMessagesPath() {
		Path pathDir = Paths.get("src", "main", "resourses");

		Path actual = new PathStorageImpl(pathDir).getMessagesPath();

		Path expected = Paths.get("src", "main", "resourses", "settings", "messages.properties");

		assertEquals(expected, actual);
	}

	@Test
	void testGetConnectionSettingPath() {
		Path pathDir = Paths.get("src", "main", "resourses");

		Path actual = new PathStorageImpl(pathDir).getConnectionSettingPath();

		Path expected = Paths.get("src", "main", "resourses", "dao", "dao_settings.properties");

		assertEquals(expected, actual);
	}

	@Test
	void testGetCreateTableScriptPath() {
		Path pathDir = Paths.get("src", "main", "resourses");

		Path actual = new PathStorageImpl(pathDir).getCreateTableScriptPath();

		Path expected = Paths.get("src", "main", "resourses", "dao", "scripts", "table_creation_script.sql");

		assertEquals(expected, actual);
	}

}
