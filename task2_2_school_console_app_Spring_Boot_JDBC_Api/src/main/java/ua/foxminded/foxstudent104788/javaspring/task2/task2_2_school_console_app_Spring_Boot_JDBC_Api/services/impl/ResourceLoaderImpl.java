package ua.foxminded.foxstudent104788.javaspring.task2.task2_2_school_console_app_Spring_Boot_JDBC_Api.services.impl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import ua.foxminded.foxstudent104788.javaspring.task2.task2_2_school_console_app_Spring_Boot_JDBC_Api.exceptions.ResourseException;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_2_school_console_app_Spring_Boot_JDBC_Api.models.MessageStorage;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_2_school_console_app_Spring_Boot_JDBC_Api.services.PathStorage;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_2_school_console_app_Spring_Boot_JDBC_Api.services.ResourceLoader;

/**
 * The ResourceLoaderImpl class implements the ResourceLoader interface and
 * provides methods for loading resources.
 */
@Component
public class ResourceLoaderImpl implements ResourceLoader {

	private static final Logger logger = LogManager.getLogger(ResourceLoaderImpl.class);

	PathStorage pathStorage;

	public ResourceLoaderImpl() {
		super();
		this.pathStorage = new PathStorageImpl();
	}

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
