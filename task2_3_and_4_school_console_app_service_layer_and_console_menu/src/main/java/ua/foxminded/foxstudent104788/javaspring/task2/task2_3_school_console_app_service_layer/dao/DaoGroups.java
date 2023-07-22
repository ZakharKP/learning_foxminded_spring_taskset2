package ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.dao;

import java.util.List;

import ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.models.Group;

/**
 * The DaoGroups interface provides methods for interacting with groups in a
 * database.
 */
public interface DaoGroups extends DaoEntity<Group> {

	
	/**
	 * Retrieves groups by their IDs.
	 *
	 * @param groupsIds The list of group IDs.
	 * @return A list of groups associated with the specified IDs.
	 */
	List<Group> get(List<Integer> groupsIds);
}
