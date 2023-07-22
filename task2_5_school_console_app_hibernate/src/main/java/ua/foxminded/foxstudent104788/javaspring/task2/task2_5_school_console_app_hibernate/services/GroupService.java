package ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.models.Group;
@Service
public interface GroupService {

	/**
	 * Gets an group by its ID.
	 *
	 * @param id The ID of the group.
	 * @return An Optional containing the group if found, or an empty Optional if
	 *         not found.
	 */
	Optional<Group> getGroup(Integer id);

	/**
	 * Gets List of groups by ID List.
	 *
	 * @param ID List of the group.
	 * @return List containing the groups if found, or an empty List
	 */
	List<Group> getListOfGroupByIdsList(List<Integer> ids);

	/**
	 * Retrieves all groups.
	 *
	 * @return A list of all groups.
	 */
	List<Group> getAll();

	/**
	 * Adds an group.
	 *
	 * @param group The group to be added.
	 * @return The number of rows affected (usually 1) if the group was added
	 *         successfully, or -1 if an error occurred.
	 */
	int saveGroup(Group group);

	/**
	 * Adds List of groups.
	 *
	 * @param List of groups The groups to be added.
	 * @return The number of rows affected (usually 1) if the group was added
	 *         successfully, or -1 if an error occurred.
	 */
	int saveAllGroups(List<Group> groups);

	/**
	 * Deletes an group.
	 *
	 * @param group The group to be deleted.
	 * @return The number of rows affected (usually 1) if the group was deleted
	 *         successfully, or -1 if an error occurred.
	 */
	int deleteGroup(Group group);

	/**
	 * Retrieves a table representation of the groups.
	 *
	 * @param groups The list of groups.
	 * @return A list of string arrays representing the table, with each string
	 *         array representing a row of the table.
	 */
	List<String[]> representAsTable(List<Group> groups);
	
	List<Group> findWithLessOrEqualsStudents(int count);

	boolean isEmpty();

}
