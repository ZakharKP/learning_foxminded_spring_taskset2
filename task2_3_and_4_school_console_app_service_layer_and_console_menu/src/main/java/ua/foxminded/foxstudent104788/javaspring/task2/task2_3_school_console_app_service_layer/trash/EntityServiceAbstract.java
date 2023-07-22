package ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.trash;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.dao.DaoEntity;

@AllArgsConstructor
@Getter
public abstract class EntityServiceAbstract<T> implements EntityService<T>{
		
	private DaoEntity<T> daoEntity;
	
	/**
	 * Gets an entity by its ID.
	 *
	 * @param id The ID of the entity.
	 * @return An Optional containing the entity if found, or an empty Optional if
	 *         not found.
	 */
	@Override
	public Optional<T> getCourseRoster(Integer id){		
		return daoEntity.get(id);
	}
	
	/**
	 * Gets List of entities by ID List.
	 *
	 * @param ID List of the entity.
	 * @return List containing the entities if found, or an empty List
	 */
	@Override
	public List<T> getListOfCourseRostersByIdsList(List<Integer> ids){		
		List<T> entities = new ArrayList<>();
		
		for(Integer id : ids) {
			daoEntity.get(id).ifPresent(entities :: add);
		}
		
		return entities; 
	}

	/**
	 * Retrieves all entities.
	 *
	 * @return A list of all entities.
	 */
	@Override
	public List<T> getAll(){
		return daoEntity.getAll();
	}

	/**
	 * Adds an entity.
	 *
	 * @param entity The entity to be added.
	 * @return The number of rows affected (usually 1) if the entity was added
	 *         successfully, or -1 if an error occurred.
	 */
	@Override
	public int saveCourseRoster(T entity) {
		return daoEntity.save(entity);
	}
	
	/**
	 * Adds List of entities.
	 *
	 * @param List of entities The entities to be added.
	 * @return The number of rows affected (usually 1) if the entity was added
	 *         successfully, or -1 if an error occurred.
	 */
	@Override
	public int saveAll(List<T> entities) {
		int saved = 0;
		for (T entity : entities) {
			int status = daoEntity.save(entity);
			if(status > 0) {
				saved++;
			}
		}
		return saved;
	}

	/**
	 * Deletes an entity.
	 *
	 * @param entity The entity to be deleted.
	 * @return The number of rows affected (usually 1) if the entity was deleted
	 *         successfully, or -1 if an error occurred.
	 */
	@Override
	public int deleteCourseRoster(T entity) {
		return daoEntity.delete(entity);
	}

	@Override
	public boolean isAnyCourseRosters() {
		return daoEntity.size() == 0;
	}
}
