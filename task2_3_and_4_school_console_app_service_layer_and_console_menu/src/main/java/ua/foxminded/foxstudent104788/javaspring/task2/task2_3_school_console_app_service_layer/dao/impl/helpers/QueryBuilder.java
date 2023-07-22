package ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.dao.impl.helpers;

import java.util.List;

import lombok.AllArgsConstructor;

/**
 * The QueryBuilder class provides methods to generate SQL queries for a
 * specific table.
 * 
 *
 * @param tableName the name of the table
 */
@AllArgsConstructor
public class QueryBuilder {

	private final String tableName;

	/**
	 * Generates an SQL INSERT query for inserting data into the table.
	 *
	 * @param columnNames the list of column names to insert data into
	 * @return the generated INSERT query
	 */
	public String getInsertQuery(List<String> columnNames) {
		StringBuilder query = new StringBuilder(String.format("INSERT INTO %s (", tableName));
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

	/**
	 * Generates an SQL SELECT query to retrieve all records from the table.
	 *
	 * @return the generated SELECT query
	 */
	public String getSelectAllQuery() {
		return String.format("SELECT * FROM %s ", tableName);
	}
	
	public String getSelectCountQuery() {
		return String.format("SELECT COUNT(*) FROM %s", tableName);
	}

	/**
	 * Generates an SQL SELECT query to retrieve specific columns from the table.
	 *
	 * @param columnNames the list of column names to select
	 * @return the generated SELECT query
	 */
	public String getSelectColumnsQuery(List<String> columnNames) {
		StringBuilder query = new StringBuilder("SELECT ");
		for (String columnName : columnNames) {
			query.append(columnName);
			query.append(", ");
		}

		deleteLastComa(query);
		query.append(String.format(" FROM %s ", tableName));
		return query.toString();
	}

	/**
	 * Generates an SQL DELETE query to delete all records from the table.
	 *
	 * @return the generated DELETE query
	 */
	public String getDeleteQuery() {
		return String.format("DELETE FROM %s ", tableName);
	}

	/**
	 * Generates an SQL WHERE clause for filtering records based on multiple column
	 * values.
	 *
	 * @param columnNames the list of column names to filter on
	 * @return the generated WHERE clause
	 */
	public String getWhere(List<String> columnNames) {
		StringBuilder query = new StringBuilder("WHERE ");

		for (String columnName : columnNames) {
			query.append(columnName);
			query.append(" = ");
			query.append("?");
			query.append(" AND ");

		}
		deleteLastAnd(query);

		return query.toString();
	}

	/**
	 * Generates an SQL WHERE clause for filtering records based on a single column
	 * value.
	 *
	 * @param columnName the name of the column to filter on
	 * @return the generated WHERE clause
	 */
	public String getWhere(String columnName) {
		StringBuilder query = new StringBuilder("WHERE ");

		query.append(columnName);
		query.append(" = ");
		query.append("?");

		return query.toString();
	}

	/**
	 * Generates an SQL query to count the number of records grouped by a column and
	 * satisfying a condition.
	 *
	 * @param columnCount the column to count
	 * @param lessOrMore  the comparison operator (e.g., <, >)
	 * @return the generated COUNT query
	 */
	public String getGroupByCountQuery(String columnCount, String lessOrMore) {

		return String.format("GROUP BY %s HAVING COUNT(%s) %s ?", columnCount, columnCount, lessOrMore);
	}

	/**
	 * Deletes the last "AND" keyword from the StringBuilder query.
	 *
	 * @param query the StringBuilder query
	 */
	private void deleteLastAnd(StringBuilder query) {
		query.delete(query.length() - 5, query.length());

	}

	/**
	 * Deletes the last comma from the StringBuilder query.
	 *
	 * @param query the StringBuilder query
	 */
	private void deleteLastComa(StringBuilder query) {
		query.delete(query.length() - 2, query.length());
	}
}
