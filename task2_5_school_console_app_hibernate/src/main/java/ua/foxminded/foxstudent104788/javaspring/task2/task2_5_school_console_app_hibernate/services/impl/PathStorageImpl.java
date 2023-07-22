package ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.services.impl;

import java.nio.file.Path;
import java.nio.file.Paths;

import lombok.Getter;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.services.PathStorage;

/**
 * Implementation of the PathStorage interface that provides paths to various
 * resources.
 */

@Getter
public class PathStorageImpl implements PathStorage {

	public PathStorageImpl() {
		super();
		this.resourseDir = Paths.get("src", "main", "resources");
	}

	private Path resourseDir;

	/**
	 * Retrieves the path to the menu file.
	 *
	 * @return The path to the menu file.
	 */
	@Override
	public Path getMenuPath() {

		return Paths.get(resourseDir.toString(), "settings", "menu.txt");
	}

	/**
	 * Retrieves the path to the messages properties file.
	 *
	 * @return The path to the messages properties file.
	 */
	@Override
	public Path getMessagesPath() {

		return Paths.get(resourseDir.toString(), "settings", "messages.properties");
	}

	/**
	 * Retrieves the path to the table creation script file.
	 *
	 * @return The path to the table creation script file.
	 */
	@Override
	public Path getCreateTableScriptPath() {

		return Paths.get(resourseDir.toString(), "dao", "scripts", "table_creation_script.sql");
	}

}
