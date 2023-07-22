/*
 * package ua.foxminded.foxstudent104788.javaspring.task2.
 * task2_5_school_console_app_hibernate;
 * 
 * import static org.junit.jupiter.api.Assertions.*;
 * 
 * import java.util.Optional;
 * 
 * import org.junit.jupiter.api.Test; import
 * org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
 * import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
 * import org.springframework.boot.test.context.SpringBootTest; import
 * org.springframework.context.annotation.ComponentScan; import
 * org.springframework.context.annotation.FilterType; import
 * org.springframework.test.context.jdbc.Sql;
 * 
 * import ua.foxminded.foxstudent104788.javaspring.task2.
 * task2_5_school_console_app_hibernate.dao.impl.DaoCoursesImpl; import
 * ua.foxminded.foxstudent104788.javaspring.task2.
 * task2_5_school_console_app_hibernate.dao.impl.DaoGroupsImpl; import
 * ua.foxminded.foxstudent104788.javaspring.task2.
 * task2_5_school_console_app_hibernate.dao.impl.DaoStudentsImpl; import
 * ua.foxminded.foxstudent104788.javaspring.task2.
 * task2_5_school_console_app_hibernate.models.Student; import
 * ua.foxminded.foxstudent104788.javaspring.task2.
 * task2_5_school_console_app_hibernate.services.ApplicationService; import
 * ua.foxminded.foxstudent104788.javaspring.task2.
 * task2_5_school_console_app_hibernate.services.entityservices.StudentService;
 * import ua.foxminded.foxstudent104788.javaspring.task2.
 * task2_5_school_console_app_hibernate.services.testdata.TestDataPlaceholder;
 * 
 * @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
 * 
 * @SpringBootTest()
 * 
 * @Sql(scripts = { "/scripts/table_creation_script.sql" }, executionPhase =
 * Sql.ExecutionPhase.BEFORE_TEST_METHOD) class MainTest {
 * 
 * @Autowired ApplicationService service;
 * 
 * @Autowired TestDataPlaceholder placeholder;
 * 
 * // @Autowired // StudentService studentService;
 * 
 * @Test void Run() { service.run(); fail("Not yet implemented"); }
 * 
 * @Test void Del() { placeholder.generateData(); service.delStudent(5); //
 * Optional<Student> student = studentService.getStudent(5); //
 * assertNull(student); fail("Not yet implemented"); }
 * 
 * }
 */