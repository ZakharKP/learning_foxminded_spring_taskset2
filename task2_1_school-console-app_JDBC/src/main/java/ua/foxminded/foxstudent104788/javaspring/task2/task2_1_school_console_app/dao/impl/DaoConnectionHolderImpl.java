package ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.dao.impl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import lombok.AllArgsConstructor;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.dao.DaoConnectionHolder;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.exceptions.DaoException;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.exceptions.ResourseException;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.models.DaoConnectionSettings;

@AllArgsConstructor
/**
 * Implementation of the DaoConnectionHolder interface. Manages the database
 * connection and executes SQL statements.
 */
public class DaoConnectionHolderImpl implements DaoConnectionHolder {

	private static final Logger logger = LogManager.getLogger(DaoConnectionHolderImpl.class);

	private DaoConnectionSettings settings;

	/**
	 * Executes a batch of update statements using the provided statements map.
	 *
	 * @param statements a map of statement parameters and SQL queries
	 * @return the number of affected rows
	 * @throws ResourseException if a resource-related exception occurs
	 * @throws DaoException      if a database-related exception occurs
	 */
	@Override
	public int updateExecute(Map<Object[], String> statements) {
		String url = settings.getUrl();
		String userName = settings.getUserName();
		String password = settings.getPassword();
		int status = -1;

		try {
			Class.forName("org.postgresql.Driver");

			try (Connection connection = DriverManager.getConnection(url, userName, password)) {
				for (Map.Entry<Object[], String> stat : statements.entrySet()) {
					try (PreparedStatement statement = connection.prepareStatement(stat.getValue())) {
						setParameters(statement, stat.getKey());

						status = statement.executeUpdate();
						if (status < 0) {
							return -1;
						}
					}
				}
			}

			return status;
		} catch (ClassNotFoundException e) {
			logger.error("updateExecute: ClassNotFound", e);
			throw new ResourseException(e);
		} catch (SQLException e) {
			logger.error("updateExecute: SQL", e);
			throw new DaoException(e);
		}
	}

	/**
	 * Executes an update statement with the given query and parameters.
	 *
	 * @param query      the SQL query
	 * @param parameters the query parameters
	 * @return the number of affected rows
	 * @throws ResourseException if a resource-related exception occurs
	 * @throws DaoException      if a database-related exception occurs
	 */
	@Override
	public int updateExecute(String query, Object... parameters) {

		String url = settings.getUrl();
		String userName = settings.getUserName();
		String password = settings.getPassword();

		try {
			Class.forName("org.postgresql.Driver");

			try (Connection connection = DriverManager.getConnection(url, userName, password)) {

				try (PreparedStatement statement = connection.prepareStatement(query)) {

					setParameters(statement, parameters);

					int status = statement.executeUpdate();
					return status;
				}
			}

		} catch (ClassNotFoundException e) {
			logger.error("updateExecute: ClassNotFound", e);
			throw new ResourseException(e);
		} catch (SQLException e) {
			logger.error("updateExecute: SQL", e);
			throw new DaoException(e);
		}

	}

	/**
	 * Executes a select statement with the given query and parameters.
	 *
	 * @param query      the SQL query
	 * @param parameters the query parameters
	 * @return a list of maps representing the result set
	 * @throws ResourseException if a resource-related exception occurs
	 * @throws DaoException      if a database-related exception occurs
	 */
	@Override
	public List<Map<String, String>> selectExecute(String query, Object... parameters) {

		String url = settings.getUrl();
		String userName = settings.getUserName();
		String password = settings.getPassword();
		ResultSetMetaData metaData = null;

		try {
			Class.forName("org.postgresql.Driver");

			try (Connection connection = DriverManager.getConnection(url, userName, password)) {

				try (PreparedStatement statement = connection.prepareStatement(query)) {

					setParameters(statement, parameters);

					try (ResultSet resultSet = statement.executeQuery()) {

						metaData = resultSet.getMetaData();

						if (metaData.getColumnCount() == 0) {
							return null;
						}

						return getAnswer(resultSet, metaData);
					}
				}
			}

		} catch (ClassNotFoundException e) {
			logger.error("selectExecute: ClassNotFound", e);
			throw new ResourseException(e);
		} catch (SQLException e) {
			logger.error("selectExecute: SQL", e);
			throw new DaoException(e);
		}

	}

	/**
	 * Executes a select statement with the given query and list of parameter
	 * arrays.
	 *
	 * @param query          the SQL query
	 * @param parametersList a list of parameter arrays
	 * @return a list of maps representing the result set
	 * @throws ResourseException if a resource-related exception occurs
	 * @throws DaoException      if a database-related exception occurs
	 */
	@Override
	public List<Map<String, String>> selectExecute(String query, List<Object[]> parametersList) {

		String url = settings.getUrl();
		String userName = settings.getUserName();
		String password = settings.getPassword();
		ResultSetMetaData metaData = null;
		List<Map<String, String>> answer = new ArrayList<>();

		try {
			Class.forName("org.postgresql.Driver");

			try (Connection connection = DriverManager.getConnection(url, userName, password)) {

				try (PreparedStatement statement = connection.prepareStatement(query)) {
					for (Object[] parameters : parametersList) {
						setParameters(statement, parameters);

						try (ResultSet resultSet = statement.executeQuery()) {

							metaData = resultSet.getMetaData();

							if (metaData.getColumnCount() == 0) {
								return null;
							}

							if (answer.isEmpty()) {
								answer.add(getTableName(metaData));
							}

							while (resultSet.next()) {
								answer.add(getRow(resultSet, metaData));
							}

						}
					}
					return answer;
				}

			}

		} catch (ClassNotFoundException e) {
			logger.error("selectExecute: ClassNotFound", e);
			throw new ResourseException(e);
		} catch (SQLException e) {
			logger.error("selectExecute: SQL", e);
			throw new DaoException(e);
		}

	}

	private List<Map<String, String>> getAnswer(ResultSet resultSet, ResultSetMetaData metaData) throws SQLException {

		List<Map<String, String>> answer = new ArrayList<>();

		answer.add(getTableName(metaData));
		while (resultSet.next()) {
			answer.add(getRow(resultSet, metaData));
		}
		return answer;
	}

	private Map<String, String> getRow(ResultSet resultSet, ResultSetMetaData metaData) throws SQLException {
		String columnName = null;
		String columnValue = null;
		Map<String, String> row = new HashMap<>();

		for (int i = 1; i <= metaData.getColumnCount(); i++) {
			columnName = metaData.getColumnName(i);
			columnValue = resultSet.getString(columnName);
			row.put(columnName, columnValue);
		}

		return row;

	}

	private Map<String, String> getTableName(ResultSetMetaData metaData) throws SQLException {
		Map<String, String> tableName = new HashMap<>();

		tableName.put("table_name", metaData.getTableName(1));

		return tableName;
	}

	/**
	 * Executes an SQL script from the specified file path.
	 *
	 * @param path the path to the SQL script file
	 * @throws DaoException      if a database-related exception occurs
	 * @throws ResourseException if a resource-related exception occurs
	 */
	@Override
	public void executeScript(Path path) {
		String url = settings.getUrl();
		String userName = settings.getUserName();
		String password = settings.getPassword();

		try (Connection connection = DriverManager.getConnection(url, userName, password);
				BufferedReader reader = new BufferedReader(new FileReader(path.toFile()))) {

			Class.forName("org.postgresql.Driver");

			ScriptRunner script = new ScriptRunner(connection);

			script.runScript(reader);
		} catch (SQLException e) {
			logger.error("executeScript(): SQL - " + e.getMessage(), e);
			throw new DaoException(e);
		} catch (ClassNotFoundException | IOException e) {
			logger.error("executeScript(): ClassNotFound - " + e.getMessage(), e);
			throw new ResourseException(e);
		}
	}

	private void setParameters(PreparedStatement statement, Object... parameters) throws SQLException {
		for (int i = 0; i < parameters.length; i++) {
			statement.setObject(i + 1, parameters[i]);
		}

	}

}
