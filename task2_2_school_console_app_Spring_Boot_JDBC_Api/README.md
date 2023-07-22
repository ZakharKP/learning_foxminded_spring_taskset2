# Task 2.2  JDBC Api

## Assignment:

Based on previously created console application sql-jdbc-school, create spring boot application utilizing spring JDBC API.

Use already existing tests with spring test tools like @Sql

No need for db drop functionality


## Project bootstrap:

Use Initializer to bootstrap project with following dependencies:

JDBC API (Database Connectivity API that defines how a client may connect and query a database.) 
https://www.baeldung.com/spring-jdbc-jdbctemplate
https://www.concretepage.com/spring-5/sql-example-spring-test
Flyway Migration (Version control for your database so you can migrate from any version (incl. an empty database) to the latest version of the schema.)
PostgreSQL Database (The use of docker is recommended)
Use Testcontainers for JdbcTests to instantiate a test database
Database structure:

## Tables (the given types are Java types, use SQL analogs that fit the most:

```

groups(
	group_id int,
	group_name string
)
students(
	student_id int,
	group_id int,
	first_name string,
	last_name string
)
courses(
	course_id int,
	course_name string,
	course_description string
)
Create a DAO layer using JdbcTemplate and implement the basic CRUD functionality

package com.foxminded.spring.console.dao;

@Repository
public class JdbcCourseDao implements CourseDao {

    public static final String FIND_BY_NAME = "select * from courses where course_name = ?";
    
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Course> courseRowMapper = new CourseRowMapper();

    public JdbcCourseDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    @Override
    Optional<Course> findByName(String name) {
        try {
            return Optional.of(jdbcTemplate
                    .queryForObject(FIND_BY_NAME, courseRowMapper, name))
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
    
    // rest of code
}
Testing
Use test containers to run your tests
/src/test/resources/application.yml

spring:
  datasource:
    driver-class-name: org.testcontainers.jdbc.ContainerDatabaseDriver
    url: jdbc:tc:postgresql:14.6:///test?currentSchema=student_db&TC_REUSABLE=true
    username: root
    password: test
    hikari:
      minimum-idle: 1
      maximum-pool-size: 5
Then run your tests like this:

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(
        scripts = {"/sql/clear_tables.sql", "/sql/sample_data.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
class JDBCStudentDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private StudentDao dao;

    @BeforeEach
    void setUp() {
        dao = new JDBCStudentDao(jdbcTemplate);
    }

    // tests here
}

```


