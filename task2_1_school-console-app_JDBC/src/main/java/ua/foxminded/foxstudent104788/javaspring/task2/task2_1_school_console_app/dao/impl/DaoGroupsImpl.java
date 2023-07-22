package ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.dao.DaoConnectionHolder;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.dao.DaoGroups;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.models.Group;

@AllArgsConstructor
public class DaoGroupsImpl implements DaoGroups {

	DaoConnectionHolder connection;

	/**
	 * Adds a group to the database.
	 *
	 * @param group The group to be added.
	 * @return The number of rows affected (usually 1) if the group was added
	 *         successfully, or -1 if an error occurred.
	 */
	@Override
	public int addGroup(Group group) {
		String query = null;
		Object[] parametres = null;
		List<String> columnNames = getColumnNames(group);

		if (columnNames.isEmpty()) {
			return -1;
		}

		query = getInsertQuery(columnNames);
		parametres = getParametres(columnNames.size(), group);

		return connection.updateExecute(query, parametres);
	}

	/**
	 * Adds multiple groups to the database.
	 *
	 * @param groups The list of groups to be added.
	 * @return The number of rows affected if the groups were added successfully, or
	 *         -1 if an error occurred.
	 */
	@Override
	public int addGroups(List<Group> groups) {
		Map<Object[], String> statement = new HashMap<>();
		String query = null;
		Object[] parametres = null;
		List<String> columnNames;
		for (Group group : groups) {
			columnNames = getColumnNames(group);
			if (columnNames.isEmpty()) {
				return -1;
			}

			query = getInsertQuery(columnNames);
			parametres = getParametres(columnNames.size(), group);
			statement.put(parametres, query);
		}

		return connection.updateExecute(statement);
	}

	/**
	 * Retrieves a table representation of the groups.
	 *
	 * @param groups The list of groups.
	 * @return A list of string arrays representing the table, with each string
	 *         array representing a row of the table.
	 */
	@Override
	public List<String[]> getTable(List<Group> groups) {
		List<String[]> table = new ArrayList<>();
		table.add(new String[] { "group_id", "group_name" });
		for (Group group : groups) {
			table.add(new String[] { String.valueOf(group.getId()), String.valueOf(group.getGroupName()) });
		}

		return table;
	}

	/**
	 * Retrieves groups by their IDs.
	 *
	 * @param groupsIds The list of group IDs.
	 * @return A list of groups associated with the specified IDs.
	 */
	@Override
	public List<Group> getById(List<Integer> groupsIds) {
		StringBuilder query = new StringBuilder();

		Group group;
		List<String> columnNames = new ArrayList<>();

		List<Object[]> parametres = new ArrayList<>();

		for (Integer groupId : groupsIds) {
			group = new Group(groupId, null);
			if (columnNames.isEmpty()) {
				columnNames.addAll(getColumnNames(group));
			}
			parametres.add(getParametres(columnNames.size(), group));
		}

		query.append(getSelectAllQuery());
		query.append(getWhere(columnNames));

		List<Map<String, String>> groupsData = connection.selectExecute(query.toString(), parametres);
		List<Group> groups = getGroups(groupsData);

		return groups;

	}

	private String getWhere(List<String> columnNames) {
		StringBuilder query = new StringBuilder(" WHERE ");

		for (String columnName : columnNames) {
			query.append(columnName);
			query.append(" = ");
			query.append("?");
			query.append(" AND ");

		}
		deleteLastAnd(query);

		return query.toString();
	}

	private String getSelectAllQuery() {

		return "SELECT * FROM groups";
	}

	private String getInsertQuery(List<String> columnNames) {
		StringBuilder query = new StringBuilder("INSERT INTO groups (");
		StringBuilder values = new StringBuilder(" VALUES (");
		for (String columnName : columnNames) {
			query.append(columnName);
			query.append(", ");
			values.append("?");
			values.append(", ");
		}
		deleteLastComa(query);
		deleteLastComa(values);
		query.append(")");
		values.append(")");
		query.append(values.toString());
		return query.toString();
	}

	private List<Group> getGroups(List<Map<String, String>> groupsData) {
		List<Group> groups = new ArrayList<>();
		Integer id;
		String groupName;

		if (!groupsData.get(0).get("table_name").equals("groups")) {
			return groups;
		}

		for (int i = 1; i < groupsData.size(); i++) {
			id = groupsData.get(i).get("group_id") == null ? null : Integer.parseInt(groupsData.get(i).get("group_id"));
			groupName = groupsData.get(i).get("group_name") == null ? null : groupsData.get(i).get("group_name");

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

	private void deleteLastAnd(StringBuilder query) {
		query.delete(query.length() - 5, query.length());

	}

	private void deleteLastComa(StringBuilder query) {
		query.delete(query.length() - 2, query.length());
	}

}
