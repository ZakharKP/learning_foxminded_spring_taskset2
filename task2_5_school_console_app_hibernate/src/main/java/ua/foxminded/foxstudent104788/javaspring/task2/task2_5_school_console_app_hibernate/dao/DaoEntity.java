package ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.dao;

import java.util.List;
import java.util.Optional;

/**
 * The DaoEntity interface provides methods for accessing and manipulating
 * entities.
 *
 * @param <T> The type of the entity.
 */
public interface DaoEntity<T> {
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
	 * Update an entity.
	 *
	 * @param entity The entity to be updated.
	 * @return The number of rows affected (usually 1) if the entity was updated
	 *         successfully, or -1 if an error occurred.
	 */
	int update(T entity);

	/**
	 * Return Row Count of Table.
	 * 
	 * @return The number of rows exist in the table
	 */
	long size();

}