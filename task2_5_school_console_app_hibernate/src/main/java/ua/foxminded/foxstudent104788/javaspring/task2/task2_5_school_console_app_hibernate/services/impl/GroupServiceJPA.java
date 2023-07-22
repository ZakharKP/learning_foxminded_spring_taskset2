
package ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.dao.DaoGroups;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.models.Constants;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.models.Group;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.services.GroupService;

@Log4j2
@Service
public class GroupServiceJPA implements GroupService {

	@Autowired
	private DaoGroups daoGroups;

	/**
	 * Retrieves a table representation of the groups.
	 *
	 * @param groups The list of groups.
	 * @return A list of string arrays representing the table, with each string
	 *         array representing a row of the table.
	 */
	@Override
	public List<String[]> representAsTable(List<Group> groups) {

		log.info("preparing table of groups");

		List<String[]> table = new ArrayList<>();
		table.add(
				new String[] { Constants.GROUP_COLUMN_NAME_ID, Constants.GROUP_COLUMN_NAME_NAME, "student's amount" });
		for (Group group : groups) {
			table.add(new String[] { String.valueOf(group.getId()), String.valueOf(group.getGroupName()),
					String.valueOf(group.getStudents().size()) });
		}

		return table;
	}

	/**
	 * Gets an group by its ID.
	 *
	 * @param id The ID of the group.
	 * @return An Optional containing the group if found, or an empty Optional if
	 *         not found.
	 */

	@Override

	public Optional<Group> getGroup(Integer id) {

		log.info("Start searching of Group by id=" + id);

		return daoGroups.get(id);
	}

	/**
	 * Gets List of groups by ID List.
	 *
	 * @param ID List of the group.
	 * @return List containing the groups if found, or an empty List
	 */

	@Override

	public List<Group> getListOfGroupByIdsList(List<Integer> ids) {

		log.info("Start searching of Groups by id List");

		List<Group> groups = new ArrayList<>();

		for (Integer id : ids) {
			daoGroups.get(id).ifPresent(groups::add);
		}

		return groups;
	}

	/**
	 * Retrieves all groups.
	 *
	 * @return A list of all groups.
	 */

	@Override

	public List<Group> getAll() {

		log.info("Start searching of All Groups");

		return daoGroups.getAll();
	}

	/**
	 * Adds an group.
	 *
	 * @param group The group to be added.
	 * @return The number of rows affected (usually 1) if the group was added
	 *         successfully, or -1 if an error occurred.
	 */

	@Override

	public int saveGroup(Group group) {

		log.info(String.format("Start saving new group: id=%s, name=%s", group.getId(), group.getGroupName()));

		return daoGroups.save(group);
	}

	/**
	 * Adds List of groups.
	 *
	 * @param List of groups The groups to be added.
	 * @return The number of rows affected (usually 1) if the group was added
	 *         successfully, or -1 if an error occurred.
	 */

	@Override

	public int saveAllGroups(List<Group> groups) {

		log.info(String.format("Start saving List of %s groups:", groups.size()));

		int saved = 0;
		for (Group group : groups) {
			int status = daoGroups.save(group);
			if (status > 0) {
				saved++;
			}
		}

		log.info(saved + " new groups was saved");

		return saved;
	}

	/**
	 * Deletes an group.
	 *
	 * @param group The group to be deleted.
	 * @return The number of rows affected (usually 1) if the group was deleted
	 *         successfully, or -1 if an error occurred.
	 */
	@Override

	public int deleteGroup(Group group) {

		log.info(String.format("Start deleting group: id=%s, name=%s", group.getId(), group.getGroupName()));

		return daoGroups.delete(group);
	}

	@Override

	public boolean isEmpty() {

		log.info("Start counting groups");

		return daoGroups.size() == 0;
	}

	@Override

	public List<Group> findWithLessOrEqualsStudents(int count) {

		log.info("Start searching groups with less or equals student's count = " + count);

		List<Group> groups = new ArrayList<>();

		for (Integer id = 1; id <= daoGroups.size(); id++) {
			Optional<Group> group = daoGroups.get(id);
			if (group.isPresent() && group.get().getStudents().size() <= count) {
				groups.add(group.get());
			}
		}

		return groups;
	}

}
