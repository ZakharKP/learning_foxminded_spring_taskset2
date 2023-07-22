package ua.foxminded.foxstudent104788.javaspring.task2.task2_2_school_console_app_Spring_Boot_JDBC_Api.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import lombok.AllArgsConstructor;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_2_school_console_app_Spring_Boot_JDBC_Api.dao.DaoGroups;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_2_school_console_app_Spring_Boot_JDBC_Api.dao.impl.rowmap.GroupRowMapper;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_2_school_console_app_Spring_Boot_JDBC_Api.models.Group;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_2_school_console_app_Spring_Boot_JDBC_Api.services.QueryBuilder;

/**
 * Implementation of the {@link DaoGroups} interface that interacts with the
 * database to perform CRUD operations on {@link Group} entities.
 */
@AllArgsConstructor
@Repository
public class DaoGroupsJdbc implements DaoGroups {

	private static final Logger logger = LogManager.getLogger(DaoGroupsJdbc.class);

	private final JdbcTemplate jdbcTemplate;

	private final QueryBuilder queryBuilder = new QueryBuilder("groups");

	private final RowMapper<Group> rowMapper = new GroupRowMapper();

	/**
	 * Adds a group to the database.
	 *
	 * @param group The group to be added.
	 * @return The number of rows affected (usually 1) if the group was added
	 *         successfully, or -1 if an error occurred.
	 */
	@Override
	public int save(Group group) {
		String query = null;
		Object[] parametres = null;
		List<String> columnNames = getColumnNames(group);

		if (columnNames.isEmpty()) {
			return -1;
		}

		query = queryBuilder.getInsertQuery(columnNames);
		parametres = getParametres(columnNames.size(), group);

		return jdbcTemplate.update(query, parametres);
	}

	/**
	 * Retrieves a group by its ID.
	 *
	 * @param id The ID of the group.
	 * @return An optional containing the group associated with the specified ID, or
	 *         an empty optional if no group is found.
	 */
	@Override
	public Optional<Group> get(Integer id) {
		StringBuilder query = new StringBuilder(queryBuilder.getSelectAllQuery());
		query.append(queryBuilder.getWhere("group_id"));

		try {
			return Optional.of(jdbcTemplate.queryForObject(query.toString(), rowMapper, id));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	/**
	 * Retrieves groups by their IDs.
	 *
	 * @param groupsIds The list of group IDs.
	 * @return A list of groups associated with the specified IDs.
	 */
	@Override
	public List<Group> get(List<Integer> groupsIds) {
		List<Group> groups = new ArrayList<>();

		for (Integer groupId : groupsIds) {
			groups.add(get(groupId).get());
		}

		return groups;

	}

	/**
	 * Retrieves all groups from the database.
	 *
	 * @return A list of all groups.
	 */
	@Override
	public List<Group> getAll() {
		List<Map<String, Object>> groupsData = jdbcTemplate.queryForList(queryBuilder.getSelectAllQuery());

		return getGroups(groupsData);
	}

	/**
	 * Deletes a group from the database.
	 *
	 * @param group The group to be deleted.
	 * @return The number of rows affected (usually 1) if the group was deleted
	 *         successfully, or -1 if an error occurred.
	 */
	@Override
	public int delete(Group group) {
		StringBuilder query = new StringBuilder();
		Object[] parametres = null;
		List<String> columnNames = getColumnNames(group);
		parametres = getParametres(columnNames.size(), group);

		if (columnNames.isEmpty()) {
			return -1;
		}

		query.append(queryBuilder.getDeleteQuery());
		query.append(queryBuilder.getWhere(columnNames));

		return jdbcTemplate.update(query.toString(), parametres);
	}

	/**
	 * Retrieves a table representation of the groups.
	 *
	 * @param groups The list of groups.
	 * @return A list of string arrays representing the table, with each string
	 *         array representing a row of the table.
	 */
	@Override
	public List<String[]> representAsTable(List<Group> groups) {
		List<String[]> table = new ArrayList<>();
		table.add(new String[] { "group_id", "group_name" });
		for (Group group : groups) {
			table.add(new String[] { String.valueOf(group.getId()), String.valueOf(group.getGroupName()) });
		}

		return table;
	}

	private List<Group> getGroups(List<Map<String, Object>> groupsData) {
		List<Group> groups = new ArrayList<>();
		Integer id;
		String groupName;

		for (int i = 0; i < groupsData.size(); i++) {
			id = groupsData.get(i).get("group_id") == null ? null : (Integer) groupsData.get(i).get("group_id");
			groupName = groupsData.get(i).get("group_name") == null ? null
					: (String) groupsData.get(i).get("group_name");

			groups.add(new Group(id, groupName));
		}

		return groups;
	}

	private Object[] getParametres(int size, Group group) {
		Object[] parametres = new Object[size];
		int i = 0;
		if (group.getId() != null) {
			parametres[i] = group.getId();
			i++;
		}
		if (group.getGroupName() != null) {
			parametres[i] = group.getGroupName();
		}

		return parametres;
	}

	private List<String> getColumnNames(Group group) {
		List<String> columnNames = new ArrayList<>();
		if (group.getId() != null) {
			columnNames.add("group_id");
		}
		if (group.getGroupName() != null) {
			columnNames.add("group_name");
		}

		return columnNames;
	}

}
