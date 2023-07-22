
package ua.foxminded.foxstudent104788.javaspring.task2.task2_6_school_console_app_spring_data_jpa.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_6_school_console_app_spring_data_jpa.models.Constants;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_6_school_console_app_spring_data_jpa.models.Group;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_6_school_console_app_spring_data_jpa.repository.GroupsRepository;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_6_school_console_app_spring_data_jpa.services.GroupService;

@Log4j2
@Service
public class GroupServiceImpl implements GroupService {

	@Autowired
	private GroupsRepository groupsRepository;

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

		return groupsRepository.findById(id);
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

		return groupsRepository.findAllById(ids);
	}

	/**
	 * Retrieves all groups.
	 *
	 * @return A list of all groups.
	 */

	@Override

	public List<Group> getAll() {

		log.info("Start searching of All Groups");

		return groupsRepository.findAll();
	}

	/**
	 * Adds an group.
	 *
	 * @param group The group to be added.
	 * @return The new group entity if the group was added successfully, or null if
	 *         an error occurred.
	 */

	@Override

	public Group saveGroup(Group group) {

		if (group == null) {
			log.info("Can't save - group is NULL");
			return null;
		}

		log.info("Start saving new " + group.toString());

		Group savedGroup = null;

		try {
			savedGroup = groupsRepository.save(group);
		} catch (IllegalArgumentException e) {
			log.error("Something went wrong trying to save group" + e.getMessage());
			return null;
		}

		log.info("Sucesfully saved " + group.toString());
		return savedGroup;
	}

	/**
	 * Adds List of groups.
	 *
	 * @param List of groups The groups to be added.
	 * @return The List of saved Groups if the groups was added successfully, or
	 *         null if an error occurred.
	 */

	@Override

	public List<Group> saveAllGroups(List<Group> groups) {

		if (groups == null) {
			log.info("Can't save - groups is NULL");
			return null;
		}

		log.info(String.format("Start saving List of %s groups:", groups.size()));

		List<Group> savedGroups = groupsRepository.saveAll(groups);

		log.info(savedGroups.size() + " new groups was saved");

		return savedGroups;
	}

	/**
	 * Deletes an group.
	 *
	 * @param group The group to be deleted.
	 */
	@Override

	public void deleteGroup(Group group) {
		if (group == null) {
			log.info("Can't delete - group is NULL");
			return;
		}

		log.info("Start deleting " + group.toString());
		try {
			groupsRepository.delete(group);
			log.info("Sucesfully deleted");
		} catch (IllegalArgumentException e) {
			log.error("Something went wrong trying to delete group" + e.getMessage());
		}

	}

	@Override

	public boolean isEmpty() {

		log.info("Start counting groups");

		return groupsRepository.count() == 0;
	}

	@Override

	public List<Group> findWithLessOrEqualsStudents(int count) {

		log.info("Start searching groups with less or equals student's count = " + count);

		List<Group> groups = new ArrayList<>();

		for (Integer id = 1; id <= groupsRepository.count(); id++) {
			Optional<Group> group = groupsRepository.findById(id);
			if (group.isPresent() && group.get().getStudents().size() <= count) {
				groups.add(group.get());
			}
		}

		return groups;
	}

}
