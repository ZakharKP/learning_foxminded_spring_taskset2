package ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.dao;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

/**
 * This interface represents a holder for a database connection and provides
 * methods for executing queries and scripts.
 */
public interface DaoConnectionHolder {

	/**
	 * Executes a SELECT query with optional parameters and returns the result as a
	 * list of maps.
	 *
	 * @param statement  the SQL SELECT statement
	 * @param parameters optional parameters to be set in the query
	 * @return a list of maps representing the query result, where each map
	 *         represents a row of data with column names as keys, and the map at
	 *         index 0 contains the table name with the key "table_name"
	 */
	List<Map<String, String>> selectExecute(String statement, Object... parameters);

	/**
	 * Executes an UPDATE query with optional parameters and returns the number of
	 * affected rows.
	 *
	 * @param query      the SQL UPDATE statement
	 * @param parameters optional parameters to be set in the query
	 * @return the number of affected rows
	 */
	int updateExecute(String query, Object... parameters);

	/**
	 * Executes a SQL script located at the specified path.
	 *
	 * @param path the path to the SQL script file
	 */
	void executeScript(Path path);

	/**
	 * Executes a batch of UPDATE queries with optional parameters and returns the
	 * number of affected rows.
	 *
	 * @param statement a map containing parameter arrays and corresponding SQL
	 *                  UPDATE queries
	 * @return the number of affected rows
	 */
	int updateExecute(Map<Object[], String> statement);

	/**
	 * Executes a SELECT query with a list of parameter arrays and returns the
	 * result as a list of maps.
	 *
	 * @param query          the SQL SELECT statement
	 * @param parametersList a list of parameter arrays
	 * @return a list of maps representing the query result, where each map
	 *         represents a row of data with column names as keys, and the map at
	 *         index 0 contains the table name with the key "table_name"
	 */
	List<Map<String, String>> selectExecute(String query, List<Object[]> parametersList);
}