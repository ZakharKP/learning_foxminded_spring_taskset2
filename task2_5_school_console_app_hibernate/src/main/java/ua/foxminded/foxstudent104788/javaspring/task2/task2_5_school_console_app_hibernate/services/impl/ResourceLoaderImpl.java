package ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.services.impl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.exceptions.ResourseException;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.models.MessageStorage;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.services.PathStorage;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.services.ResourceLoader;

/**
 * The ResourceLoaderImpl class implements the ResourceLoader interface and
 * provides methods for loading resources.
 */
@Log4j2
@Component
public class ResourceLoaderImpl implements ResourceLoader {

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

		log.info("Downloading message Storage");

		List<String> menu;

		Map<String, String> messages = getMapFromFile(pathStorage.getMessagesPath());
		try {
			menu = Files.readAllLines(pathStorage.getMenuPath());
		} catch (IOException e) {
			log.error("Can't get menu.txt");
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

		log.info("starting execute map from *.property file");

		Map<String, String> map = new HashMap<>();
		Properties properties = new Properties();

		try (InputStream inputStream = Files.newInputStream(path)) {
			properties.load(inputStream);
		} catch (IOException e) {
			log.error("Can't get Map");
			throw new ResourseException(e);
		}

		for (String key : properties.stringPropertyNames()) {
			map.put(key, properties.getProperty(key));
		}
		return map;
	}

}
