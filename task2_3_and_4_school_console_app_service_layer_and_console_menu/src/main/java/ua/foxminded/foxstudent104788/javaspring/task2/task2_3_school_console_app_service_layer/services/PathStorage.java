package ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.services;

import java.nio.file.Path;

/**
 * The PathStorage interface provides methods for retrieving paths to various
 * resources.
 */
public interface PathStorage {

	/**
	 * Retrieves the path to the menu file.
	 *
	 * @return The path to the menu file.
	 */
	Path getMenuPath();

	/**
	 * Retrieves the path to the messages properties file.
	 *
	 * @return The path to the messages properties file.
	 */
	Path getMessagesPath();

	/**
	 * Retrieves the path to the table creation script file.
	 *
	 * @return The path to the table creation script file.
	 */
	Path getCreateTableScriptPath();

}
