package ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.models;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * storage for console messages, menu of commands
 */
@AllArgsConstructor
@Getter
@Setter
public class MessageStorage {

	Map<String, String> messages;
	List<String> menu;

}
