package ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.dao.impl;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.log4j.Log4j2;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.dao.DaoCourses;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.models.Course;

/**
 * Implementation of the {@link DaoCourses} interface.
 */

@Repository
@Log4j2
public class DaoCoursesImpl implements DaoCourses {

	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Adds a single course to the database.
	 *
	 * @param course the course to add
	 * @return the number of affected rows in the database
	 */
	@Override
	@Transactional
	public int save(Course course) {

		log.info(String.format("Saving new course: id=%s, name=%s, description=%s", course.getId(),
				course.getCourseName(), course.getCourseDescription()));

		entityManager.persist(course);

		if (course.getId() != null) {
			return 1;
		} else {

			log.error("Something went wrong with saving " + course.toString());

			return -1;
		}
	}

	/**
	 * Retrieves a Course by its ID.
	 *
	 * @param id The ID of the Course to retrieve.
	 * @return An Optional containing the Course associated with the specified ID if
	 *         it exists, or an empty Optional if it doesn't exist.
	 */
	@Override
	@Transactional
	public Optional<Course> get(Integer id) {

		log.info("Getting course by id=" + id);

		try {
			Course course = entityManager.find(Course.class, id);

			log.info(course.toString() + " was finded");

			return Optional.ofNullable(course);

		} catch (NoResultException e) {

			log.info("Can't find group with id=" + id);

			return Optional.empty();
		}
	}

	/**
	 * Retrieves a list of all courses from the database.
	 *
	 * @return the list of Course objects
	 */
	@Override
	@Transactional
	public List<Course> getAll() {

		log.info("getting all courses");
		List<Course> courses = entityManager.createQuery("SELECT c FROM Course c", Course.class).getResultList();

		return courses;
	}

	/**
	 * Retrieves a Course object by its course name.
	 *
	 * @param courseName the course name
	 * @return the Course object with the given name, or a Course object with null
	 *         values if not found
	 */
	@Override
	@Transactional
	public Optional<Course> getByName(String courseName) {

		log.info("Getting course by name=" + courseName);

		try {
			return Optional.ofNullable(
					entityManager.createQuery("SELECT c FROM Course c WHERE c.courseName = :courseName", Course.class)

							.setParameter("courseName", courseName).getSingleResult());

		} catch (NoResultException e) {

			log.info("Can't find course by name=" + courseName);

			return Optional.empty();
		}

	}

	/**
	 * Deletes a Course from the database.
	 *
	 * @param course The Course to be deleted.
	 * @return The number of rows affected (usually 1) if the Course was deleted
	 *         successfully, or -1 if an error occurred.
	 */
	@Override
	@Transactional
	public int delete(Course course) {

		log.info(String.format("Deleting course: id=%s, name=%s, description=%s", course.getId(),
				course.getCourseName(), course.getCourseDescription()));

		try {
			entityManager.remove(entityManager.contains(course) ? course : entityManager.merge(course));
			log.info("Deleted successfully");

			return 1;

		} catch (IllegalArgumentException e) {

			log.info("Deleting failed. No such course: " + course.toString());
			return -1;
		}
	}

	@Override
	@Transactional
	public long size() {

		log.info("getting courses count");

		return (long) entityManager.createQuery("SELECT COUNT(c) FROM Course c").getSingleResult();

	}

	@Override
	@Transactional
	public int update(Course course) {
		log.info("Updating the" + course.toString());
		if (get(course.getId()).isPresent()) {

			entityManager.merge(course);

			return 1;
		} else {

			log.error("Ooops no such " + course.toString());

			return -1;
		}
	}

}
