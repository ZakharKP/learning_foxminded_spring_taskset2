package ua.foxminded.foxstudent104788.javaspring.task2.task2_2_school_console_app_Spring_Boot_JDBC_Api.dao;

import java.util.List;
import java.util.Optional;

/**
 * The Dao interface provides methods for accessing and manipulating entities.
 *
 * @param <T> The type of the entity.
 */
public interface Dao<T> {
	/**
	 * Gets an entity by its ID.
	 *
	 * @param id The ID of the entity.
	 * @return An Optional containing the entity if found, or an empty Optional if
	 *         not found.
	 */
	Optional<T> get(Integer id);

	/**
	 * Retrieves all entities.
	 *
	 * @return A list of all entities.
	 */
	List<T> getAll();

	/**
	 * Adds an entity.
	 *
	 * @param entity The entity to be added.
	 * @return The number of rows affected (usually 1) if the entity was added
	 *         successfully, or -1 if an error occurred.
	 */
	int save(T entity);

	/**
	 * Deletes an entity.
	 *
	 * @param entity The entity to be deleted.
	 * @return The number of rows affected (usually 1) if the entity was deleted
	 *         successfully, or -1 if an error occurred.
	 */
	int delete(T entity);

	/**
	 * Retrieves a table representation of the entities.
	 *
	 * @param entities The list of entities.
	 * @return A list of string arrays representing the table, with each string
	 *         array representing a row of the table.
	 */
	List<String[]> representAsTable(List<T> entities);
}