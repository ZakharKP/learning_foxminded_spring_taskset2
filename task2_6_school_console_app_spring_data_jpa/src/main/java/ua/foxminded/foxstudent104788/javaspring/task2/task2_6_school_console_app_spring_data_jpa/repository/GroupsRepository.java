package ua.foxminded.foxstudent104788.javaspring.task2.task2_6_school_console_app_spring_data_jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.foxminded.foxstudent104788.javaspring.task2.task2_6_school_console_app_spring_data_jpa.models.Group;

/**
 * The GroupsRepository interface provides methods for interacting with groups in a
 * database.
 */
@Repository
public interface GroupsRepository extends JpaRepository<Group, Integer> {}
