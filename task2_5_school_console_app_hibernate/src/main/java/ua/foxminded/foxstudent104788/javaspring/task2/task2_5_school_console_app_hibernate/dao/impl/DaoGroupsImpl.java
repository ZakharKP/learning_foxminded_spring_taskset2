package ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.dao.impl;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.log4j.Log4j2;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.dao.DaoGroups;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.models.Group;

/**
 * Implementation of the {@link DaoGroups} interface that interacts with the
 * database to perform CRUD operations on {@link Group} entities.
 */
@Log4j2
@Repository
public class DaoGroupsImpl implements DaoGroups {

	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Adds a group to the database.
	 *
	 * @param group The group to be added.
	 * @return The number of rows affected (usually 1) if the group was added
	 *         successfully, or -1 if an error occurred.
	 */
	@Override
	@Transactional
	public int save(Group group) {

		log.info(String.format("Saving new group: id=%s, name=%s", group.getId(), group.getGroupName()));

		entityManager.persist(group);

		if (group.getId() != null) {
			return 1;
		} else {

			log.error("Something went wrong with saving " + group.toString());

			return -1;
		}
	}

	/**
	 * Retrieves a group by its ID.
	 *
	 * @param id The ID of the group.
	 * @return An optional containing the group associated with the specified ID, or
	 *         an empty optional if no group is found.
	 */
	@Override
	@Transactional
	public Optional<Group> get(Integer id) {

		log.info("getting group by id=" + id);

		try {
			Group group = entityManager.find(Group.class, id);

			log.info(group.toString() + " was finded");

			return Optional.ofNullable(group);

		} catch (NoResultException e) {

			log.info("Can't find group with id=" + id);

			return Optional.empty();
		}
	}

	/**
	 * Retrieves all groups from the database.
	 *
	 * @return A list of all groups.
	 */
	@Override
	@Transactional
	public List<Group> getAll() {

		log.info("getting all groups");

		return entityManager.createQuery("SELECT g FROM Group g", Group.class).getResultList();
	}

	/**
	 * Deletes a group from the database.
	 *
	 * @param group The group to be deleted.
	 * @return The number of rows affected (usually 1) if the group was deleted
	 *         successfully, or -1 if an error occurred.
	 */
	@Override
	@Transactional
	public int delete(Group group) {

		log.info(String.format("Deleting group: id=%s, name=%s", group.getId(), group.getGroupName()));

		try {
			entityManager.remove(entityManager.contains(group) ? group : entityManager.merge(group));
			log.info("Deleted successfully");

			return 1;

		} catch (IllegalArgumentException e) {

			log.info("Deleting failed. No such group: " + group.toString());
			return -1;
		}
	}

	@Override
	@Transactional
	public long size() {

		log.info("getting groups count");

		return (long) entityManager.createQuery("SELECT COUNT(g) FROM Group g").getSingleResult();
	}

	@Override
	@Transactional
	public int update(Group group) {
		log.info("Updating the" + group.toString());

		if (get(group.getId()).isPresent()) {

			entityManager.merge(group);

			return 1;
		} else {

			log.error("Ooops no such " + group.toString());

			return -1;
		}
	}

}
