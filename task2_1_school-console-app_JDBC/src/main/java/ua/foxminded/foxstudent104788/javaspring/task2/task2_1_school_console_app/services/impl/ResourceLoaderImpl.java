package ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.services.impl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import lombok.AllArgsConstructor;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.exceptions.ResourseException;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.models.DaoConnectionSettings;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.models.MessageStorage;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.services.PathStorage;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.services.ResourceLoader;

/**
 * The ResourceLoaderImpl class implements the ResourceLoader interface and
 * provides methods for loading resources.
 */
@AllArgsConstructor
public class ResourceLoaderImpl implements ResourceLoader {

	private static final Logger logger = LogManager.getLogger(ResourceLoaderImpl.class);

	PathStorage pathStorage;

	/**
	 * Retrieves the message storage by loading messages and the menu from their
	 * respective files.
	 *
	 * @return The message storage containing the loaded messages and menu.
	 * @throws ResourseException If an error occurs while loading the resources.
	 */
	@Override
	public MessageStorage getMessageStorage() {
		List<String> menu;

		Map<String, String> messages = getMapFromFile(pathStorage.getMessagesPath());
		try {
			menu = Files.readAllLines(pathStorage.getMenuPath());
		} catch (IOException e) {
			logger.error("Can't get menu.txt");
			throw new ResourseException(e);
		}
		return new MessageStorage(messages, menu);
	}

	/**
	 * Retrieves the DAO connection settings by loading them from the connection
	 * settings file.
	 *
	 * @return The DAO connection settings.
	 * @throws ResourseException If an error occurs while loading the resources.
	 */
	@Override
	public DaoConnectionSettings getDaoConnectionSettings() {
		Map<String, String> connectionSettingsMap = getMapFromFile(pathStorage.getConnectionSettingPath());

		String url = connectionSettingsMap.get("url");
		String userName = connectionSettingsMap.get("user_name");
		String password = connectionSettingsMap.get("password");

		return new DaoConnectionSettings(url, userName, password);
	}

	/**
	 * Retrieves a map of key-value pairs by loading them from a properties file.
	 *
	 * @param path The path to the properties file.
	 * @return The map of key-value pairs loaded from the properties file.
	 * @throws ResourseException If an error occurs while loading the resources.
	 */
	@Override
	public Map<String, String> getMapFromFile(Path path) {

		Map<String, String> map = new HashMap<>();
		Properties properties = new Properties();

		try (InputStream inputStream = Files.newInputStream(path)) {
			properties.load(inputStream);
		} catch (IOException e) {
			logger.error("Can't get Map");
			throw new ResourseException(e);
		}

		for (String key : properties.stringPropertyNames()) {
			map.put(key, properties.getProperty(key));
		}
		return map;
	}

}
