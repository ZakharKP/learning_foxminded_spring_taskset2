
package ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.dao.impl;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import lombok.extern.log4j.Log4j2;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.dao.DaoStudents;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.models.Student;

/**
 * Implementation of the {@link DaoStudents} interface using JDBC and Spring
 * Boot.
 */

@Log4j2
@Repository
public class DaoStudentsImpl implements DaoStudents {

	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Adds multiple students to the database.
	 *
	 * @param students The list of students to be added.
	 * @return The number of rows affected if the students were added successfully,
	 *         or -1 if an error occurred.
	 */

	@Override
	@Transactional
	public int save(Student student) {

		log.info("Saving new student: " + student.toString());

		entityManager.persist(student);

		if (student.getId() != null) {
			return 1;
		} else {

			log.error("Something went wrong with saving " + student.toString());

			return -1;
		}
	}

	/**
	 * Retrieves a student from the database based on the given ID.
	 *
	 * @param id The ID of the student to retrieve.
	 * @return An {@link Optional} containing the student if found, or an empty
	 *         {@link Optional} if the student doesn't exist.
	 */

	@Override
	@Transactional
	public Optional<Student> get(Integer id) {

		log.info("getting student by id=" + id);

		try {
			Student student = entityManager.find(Student.class, id);

			return Optional.ofNullable(student);

		} catch (NoResultException e) {

			log.info("Can't find student with id=" + id);

			return Optional.empty();
		}
	}

	@Override
	@Transactional
	public List<Student> getAll() {

		log.info("getting all students");

		return entityManager.createQuery("SELECT s FROM Student s", Student.class).getResultList();
	}

	/**
	 * Deletes a student from the database.
	 *
	 * @param student The student to be deleted.
	 * @return The number of rows affected (usually 1) if the student was deleted
	 *         successfully, or -1 if an error occurred.
	 */

	@Override
	@Transactional
	public int delete(Student student) {

		log.info("Deleting student: " + student.toString());

		try {
			entityManager.remove(entityManager.contains(student) ? student : entityManager.merge(student));
			log.info("Deleted successfully");

			return 1;

		} catch (IllegalArgumentException e) {
			log.info("Deleting failed. No such student: " + student.toString());
			return -1;
		}
	}

	@Override
	@Transactional
	public long size() {

		log.info("counting all students");

		return (long) entityManager.createQuery("SELECT COUNT(s.id) FROM Student s").getSingleResult();

	}

	@Override
	@Transactional
	public int update(Student student) {
		log.info("Updating the" + student.toString());
		if (get(student.getId()).isPresent()) {

			entityManager.merge(student);

			return 1;
		} else {

			log.error("Ooops no such " + student.toString());

			return -1;
		}
	}

}
