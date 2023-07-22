/*
 * package ua.foxminded.foxstudent104788.javaspring.task2.
 * task2_3_school_console_app_service_layer.config;
 * 
 * import javax.sql.DataSource;
 * 
 * import org.springframework.boot.autoconfigure.EnableAutoConfiguration; import
 * org.springframework.context.annotation.Bean; import
 * org.springframework.context.annotation.ComponentScan; import
 * org.springframework.context.annotation.Configuration; import
 * org.springframework.jdbc.core.JdbcTemplate;
 * 
 * import ua.foxminded.foxstudent104788.javaspring.task2.
 * task2_3_school_console_app_service_layer.dao.DaoCourseRosters; import
 * ua.foxminded.foxstudent104788.javaspring.task2.
 * task2_3_school_console_app_service_layer.dao.DaoCourses; import
 * ua.foxminded.foxstudent104788.javaspring.task2.
 * task2_3_school_console_app_service_layer.dao.DaoGroups; import
 * ua.foxminded.foxstudent104788.javaspring.task2.
 * task2_3_school_console_app_service_layer.dao.DaoStudents; import
 * ua.foxminded.foxstudent104788.javaspring.task2.
 * task2_3_school_console_app_service_layer.dao.impl.DaoCourseRostersJdbc;
 * import ua.foxminded.foxstudent104788.javaspring.task2.
 * task2_3_school_console_app_service_layer.dao.impl.DaoCoursesJdbc; import
 * ua.foxminded.foxstudent104788.javaspring.task2.
 * task2_3_school_console_app_service_layer.dao.impl.DaoGroupsJdbc; import
 * ua.foxminded.foxstudent104788.javaspring.task2.
 * task2_3_school_console_app_service_layer.dao.impl.DaoStudentsJdbc; import
 * ua.foxminded.foxstudent104788.javaspring.task2.
 * task2_3_school_console_app_service_layer.services.ApplicationService;
 * 
 *//**
	 * The AppConfigurations class represents the connection settings for a DAO.
	 *//*
		 * @Configuration
		 * 
		 * @ComponentScan(
		 * "ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer")
		 * 
		 * @EnableAutoConfiguration public class AppConfigurations {
		 * 
		 * @Bean public JdbcTemplate jdbcTemplate(DataSource dataSource) { return new
		 * JdbcTemplate(dataSource); }
		 * 
		 * 
		 * 
		 * }
		 */