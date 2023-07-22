package ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.services.daoservices;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.jdbc.Sql;

import ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.dao.impl.DaoStudentsImpl;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.models.Student;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.services.StudentService;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.services.TestDataPlaceholder;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
		DaoStudentsImpl.class }))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = { "/scripts/table_creation_script.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
// @SpringBootTest(classes = { StudentServiceJPA.class })
@ComponentScan({ "ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.dao",
		"ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.models",
		"ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.services.daoservices",
		"ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.services.testdataservices" })
class StudentServiceJPA22Test {

	@Autowired
	StudentService studentServiceJPA;

	@Autowired
	TestDataPlaceholder placeholder;

	@BeforeEach
	void before() {
		placeholder.generateData();
	}

	@Test
	void testDeleteStudent() {

		Integer studentId = 3;

		Optional<Student> student = studentServiceJPA.getStudent(studentId);

		int status = studentServiceJPA.deleteStudent(student.get());

		Optional<Student> st = studentServiceJPA.getStudent(studentId);
		assertFalse(st.isPresent());
		// assertTrue(status > 0);

	}

	
}
