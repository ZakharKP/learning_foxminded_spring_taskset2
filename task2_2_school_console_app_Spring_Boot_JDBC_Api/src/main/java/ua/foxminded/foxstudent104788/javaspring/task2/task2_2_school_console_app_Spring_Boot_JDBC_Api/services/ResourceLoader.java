package ua.foxminded.foxstudent104788.javaspring.task2.task2_2_school_console_app_Spring_Boot_JDBC_Api.services;

import java.nio.file.Path;
import java.util.Map;

import ua.foxminded.foxstudent104788.javaspring.task2.task2_2_school_console_app_Spring_Boot_JDBC_Api.models.MessageStorage;

/**
 * The ResourceLoader interface provides methods for loading various resources.
 */
public interface ResourceLoader {

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
