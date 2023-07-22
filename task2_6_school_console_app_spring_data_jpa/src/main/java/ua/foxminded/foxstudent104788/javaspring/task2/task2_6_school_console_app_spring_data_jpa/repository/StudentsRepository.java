package ua.foxminded.foxstudent104788.javaspring.task2.task2_6_school_console_app_spring_data_jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.foxminded.foxstudent104788.javaspring.task2.task2_6_school_console_app_spring_data_jpa.models.Student;

/**
 * The StudentsRepository interface provides methods for interacting with students in a
 * database.
 */
public interface StudentsRepository extends JpaRepository<Student, Integer> {

}