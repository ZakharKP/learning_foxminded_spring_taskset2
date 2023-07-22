package ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.services;

import java.nio.file.Path;
import java.util.Map;

import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.models.DaoConnectionSettings;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.models.MessageStorage;

/**
 * The ResourceLoader interface provides methods for loading various resources.
 */
public interface ResourceLoader {

	/**
	 * Retrieves the DAO connection settings.
	 *
	 * @return The DAO connection settings.
	 */
	DaoConnectionSettings getDaoConnectionSettings();

	/**
	 * Retrieves the message storage for localization.
	 *
	 * @return The message storage.
	 */
	MessageStorage getMessageStorage();

	/**
	 * Retrieves a map of key-value pairs from a file.
	 *
	 * @param path The path to the file.
	 * @return A map of key-value pairs loaded from the file.
	 */
	Map<String, String> getMapFromFile(Path path);

}
