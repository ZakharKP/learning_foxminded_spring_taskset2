package ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.dao;

import java.util.List;

import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.models.Group;

/**
 * The DaoGroups interface provides methods for interacting with groups in a
 * database.
 */
public interface DaoGroups {

	/**
	 * Adds a group to the database.
	 *
	 * @param group The group to be added.
	 * @return The number of rows affected (usually 1) if the group was added
	 *         successfully, or -1 if an error occurred.
	 */
	int addGroup(Group group);

	/**
	 * Adds multiple groups to the database.
	 *
	 * @param groups The list of groups to be added.
	 * @return The number of rows affected if the groups were added successfully, or
	 *         -1 if an error occurred.
	 */
	int addGroups(List<Group> groups);

	/**
	 * Retrieves a table representation of the groups.
	 *
	 * @param groups The list of groups.
	 * @return A list of string arrays representing the table, with each string
	 *         array representing a row of the table.
	 */
	List<String[]> getTable(List<Group> groups);

	/**
	 * Retrieves groups by their IDs.
	 *
	 * @param groupsIds The list of group IDs.
	 * @return A list of groups associated with the specified IDs.
	 */
	List<Group> getById(List<Integer> groupsIds);
}
