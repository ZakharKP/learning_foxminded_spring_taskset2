package ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.trash;

import java.util.List;
import java.util.Optional;

public interface EntityService<T> {

	/**
	 * Gets an entity by its ID.
	 *
	 * @param id The ID of the entity.
	 * @return An Optional containing the entity if found, or an empty Optional if
	 *         not found.
	 */
	Optional<T> getCourseRoster(Integer id);
	
	/**
	 * Gets List of entities by ID List.
	 *
	 * @param ID List of the entity.
	 * @return List containing the entities if found, or an empty List
	 */
	List<T> getListOfCourseRostersByIdsList(List<Integer> ids);

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
	int saveCourseRoster(T entity);
	
	/**
	 * Adds List of entities.
	 *
	 * @param List of entities The entities to be added.
	 * @return The number of rows affected (usually 1) if the entity was added
	 *         successfully, or -1 if an error occurred.
	 */
	int saveAll(List<T> entities);
	

	/**
	 * Deletes an entity.
	 *
	 * @param entity The entity to be deleted.
	 * @return The number of rows affected (usually 1) if the entity was deleted
	 *         successfully, or -1 if an error occurred.
	 */
	int deleteCourseRoster(T entity);

	/**
	 * Retrieves a table representation of the entities.
	 *
	 * @param entities The list of entities.
	 * @return A list of string arrays representing the table, with each string
	 *         array representing a row of the table.
	 */
	List<String[]> representAsTable(List<T> entities);
	
	boolean isAnyCourseRosters();
}
