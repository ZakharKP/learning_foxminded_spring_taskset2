package ua.foxminded.foxstudent104788.javaspring.task2.task2_2_school_console_app_Spring_Boot_JDBC_Api.dao;

import java.util.List;

import ua.foxminded.foxstudent104788.javaspring.task2.task2_2_school_console_app_Spring_Boot_JDBC_Api.models.Group;

/**
 * The DaoGroups interface provides methods for interacting with groups in a
 * database.
 */
public interface DaoGroups extends Dao<Group> {

	/**
	 * Adds a group to the database.
	 *
	 * @param group The group to be added.
	 * @return The number of rows affected (usually 1) if the group was added
	 *         successfully, or -1 if an error occurred.
	 */
	int save(Group group);

	/**
	 * Retrieves a table representation of the groups.
	 *
	 * @param groups The list of groups.
	 * @return A list of string arrays representing the table, with each string
	 *         array representing a row of the table.
	 */
	List<String[]> representAsTable(List<Group> groups);

	/**
	 * Retrieves groups by their IDs.
	 *
	 * @param groupsIds The list of group IDs.
	 * @return A list of groups associated with the specified IDs.
	 */
	List<Group> get(List<Integer> groupsIds);
}
