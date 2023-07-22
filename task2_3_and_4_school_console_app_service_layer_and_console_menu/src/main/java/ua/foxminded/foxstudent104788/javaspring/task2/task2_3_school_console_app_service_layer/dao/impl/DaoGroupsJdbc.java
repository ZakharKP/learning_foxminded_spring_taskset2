package ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.dao.impl;

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
import lombok.extern.log4j.Log4j2;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.dao.DaoGroups;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.dao.impl.helpers.QueryBuilder;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.dao.impl.rowmap.GroupRowMapper;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.models.Constants;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.models.Group;

/**
 * Implementation of the {@link DaoGroups} interface that interacts with the
 * database to perform CRUD operations on {@link Group} entities.
 */
@Log4j2
@AllArgsConstructor
@Repository
public class DaoGroupsJdbc implements DaoGroups {

	private final JdbcTemplate jdbcTemplate;

	private final QueryBuilder queryBuilder = new QueryBuilder(Constants.GROUP_TABLE_NAME);

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

		log.info(String.format("Saving new group: id=%s, name=%s", group.getId(), group.getGroupName()));

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
		query.append(queryBuilder.getWhere(Constants.GROUP_COLUMN_NAME_ID));

		log.info("getting group by id=" + id);
		
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
		
		log.info("getting groups by list of ids");

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

		log.info("getting all groups");
		
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

		log.info(String.format("Deleting new group: id=%s, name=%s", group.getId(),
				group.getGroupName()));
		
		
		return jdbcTemplate.update(query.toString(), parametres);
	}

	private List<Group> getGroups(List<Map<String, Object>> groupsData) {
		List<Group> groups = new ArrayList<>();
		Integer id;
		String groupName;

		for (int i = 0; i < groupsData.size(); i++) {
			id = groupsData.get(i).get(Constants.GROUP_COLUMN_NAME_ID) == null ? null
					: (Integer) groupsData.get(i).get(Constants.GROUP_COLUMN_NAME_ID);
			groupName = groupsData.get(i).get(Constants.GROUP_COLUMN_NAME_NAME) == null ? null
					: (String) groupsData.get(i).get(Constants.GROUP_COLUMN_NAME_NAME);

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
			columnNames.add(Constants.GROUP_COLUMN_NAME_ID);
		}
		if (group.getGroupName() != null) {
			columnNames.add(Constants.GROUP_COLUMN_NAME_NAME);
		}

		return columnNames;
	}

	@Override
	public int size() {
		
		log.info("getting groups count");
		
		return jdbcTemplate.queryForObject(queryBuilder.getSelectCountQuery(), Integer.class);
	}

}
