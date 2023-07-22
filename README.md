# Task2 - Console App

This repository contains my solutions for the Console App task set of the Java Spring Development course. The tasks in this set focus on building a console application for a school management system using Java, Spring Boot, JDBC, and Hibernate.


<br>
<details>
<summary>
Task 2.1 Plain JDBC Console app
</summary>
<br>

Create a sql-jdbc-school application  that inserts/updates/deletes data in the database using JDBC.

Use PostgreSQL DB.

Important: In the next series of tasks you're going to develop a School console application. Make sure to give a repo a meaningful name (ex.: school-console-app)

Tables (the given types are Java types, use SQL analogs that fit best:

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
```

1. Create SQL files with data:

a. create a user and database. Assign all privileges on the database to the user. (DB and the user should be created before application runs)

b. create a file with tables creation

2. Create a java application

a. On startup, it should run SQL script with table creation from previously created files. If tables already exist - drop them.

b. Generate the test data:

* 10 groups with randomly generated names. The name should contain 2 characters, hyphen, 2 numbers

* Create 10 courses (math, biology, etc)

* 200 students. Take 20 first names and 20 last names and randomly combine them to generate students.

* Randomly assign students to groups. Each group could contain from 10 to 30 students. It is possible that some groups will be without students or students without groups

* Create the MANY-TO-MANY relation  between STUDENTS and COURSES tables. Randomly assign from 1 to 3 courses for each student

3. Write SQL Queries, it should be available from the console menu:

a. Find all groups with less or equal students’ number

b. Find all students related to the course with the given name

c. Add a new student

d. Delete a student by the STUDENT_ID

e. Add a student to the course (from a list)

f. Remove the student from one of their courses.

<br>
</details>

<br>
<details>
<summary>
Task 2.2  JDBC Api
</summary>
<br>

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
```

Create a DAO layer using JdbcTemplate and implement the basic CRUD functionality

```
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
```

## Testing

Use test containers to run your tests
/src/test/resources/application.yml

```
spring:
  datasource:
    driver-class-name: org.testcontainers.jdbc.ContainerDatabaseDriver
    url: jdbc:tc:postgresql:14.6:///test?currentSchema=student_db&TC_REUSABLE=true
    username: root
    password: test
    hikari:
      minimum-idle: 1
      maximum-pool-size: 5
```

Then run your tests like this:

```
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

<br>
</details>

<br>
<details>
<summary>
Task 2.3 Service layer
</summary>
<br>

## Assignment:

Create a service layer on the top of your DAO to implement the following requirements:

- Find all groups with less or equals student count

- Find all students related to the course with the given name

- Add a new student

- Delete a student by STUDENT_ID

- Add a student to the course 

- Remove the student from one of their courses

** Add missing DAO methods to accomplish services needs

Create a generator service that will be called if database is empty:

Create 10 groups with randomly generated names. The name should contain 2 characters, hyphen, 2 numbers

Create 10 courses (math, biology, etc.)

Create 200 students. Take 20 first names and 20 last names and randomly combine them to generate students.

Randomly assign students to the groups. Each group can contain from 10 to 30 students. It is possible that some groups are without students or students without groups

Create the relation MANY-TO-MANY between the tables STUDENTS and COURSES. Randomly assign from 1 to 3 courses for each student

## Hint:

Use ApplicationRunner interface as an entry point for triggering generator

## Testing

Cover your services with tests using mocked DAO layer

```
@SpringBootTest(classes = {CourseServiceImpl.class})
class CourseServiceImplTest {
    @MockBean
    CourseDao courseDao;

    @Autowired
    CourseServiceImpl courseService;

    @Test
    void shouldCreateNewCourse() {
        Course course = getCourseEntity();

        when(courseDao.findByName(course.getName())).thenReturn(Optional.empty());
        when(courseDao.create(any(Course.class))).thenReturn(course);

        CourseDto newCourseDto = CourseDto.builder().name("Math").description("Math Description").build();
        CourseDto courseDto = courseService.create(newCourseDto);

        assertNotNull(courseDto.getId());
        assertEquals(newCourseDto.getName(), courseDto.getName());
        assertEquals(newCourseDto.getDescription(), courseDto.getDescription());

        verify(courseDao).create(any(Course.class));
    }

    // rest of the tests
}

```
<br>
</details>

<br>
<details>
<summary>
Task 2.4 Console menu
</summary>
<br>
## Assignment:

Add a proper logging to the existing code
Create a console menu to utilize implemented functionality:
Find all the groups with less or equal student count

Find all the students related to the course with the given name

Add a new student

Delete student by STUDENT_ID

Add a student to the course (from the list)

Remove the student from one of their courses

Use the existing ApplicationRunner bean as an entry point for displaying the menu
<br>
</details>

<br>
<details>
<summary>
Task 2.5 Hibernate
</summary>
<br>

## Assignment
The Hibernate should be used as a provider that implements JPA specification, the Service layer should use and depend on the JPA interfaces, not the Hibernate ones.

Add the Hibernate support to your project

```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
```

## Enrich your model with JPA annotations Rewrite the DAO layer. Use Hibernate instead of Spring JDBC.
Example:
```
@Entity
@Table(name = "courses")
public class Course {
        
        @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;
                
	@Column(name = "COURSE_NAME")
	private String courseName;

	@Column(name = "COURSE_DESCRIPTION")
	private String courseDescription;

// constructors, getters, setters, etc

}
```
## Rewrite your DAO to use EntityManager instead of JDBCTemplate
Example:

```
package com.foxminded.spring.console.dao.jpa;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
public class JPACourseDao implements CourseDao {

    public static final String FIND_BY_NAME = "select c from Course c where c.courseName = :courseName";

    @PersistenceContext
    private EntityManager em;

    @Override
    Optional<Course> findByName(String name) {
        Course course = em.createQuery(FIND_BY_NAME, Course.class)
                .setParameter("courseName", name)
                .getSingleResult();
        return Optional.ofNullable(course);
    }

// rest of code

}
```

## Testing

Update your tests code with @DataJpaTest

```
@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
        JPAStudentDao.class
}))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(
        scripts = {"/sql/clear_tables.sql", "/sql/sample_data.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
class JPAStudentDaoTest {

    @Autowired
    private StudentDao dao;

    // tests here
}
```

## Important:
Your database structure shouldn’t be changed

Your tests should work with the new dao with minimal changes

<br>
</details>

<br>
<details>
<summary>
Task 2.6 Spring Data JPA
</summary>
<br>

## Assignment

Rewrite the DAO layer. Use Spring Data JPA instead of Hibernate.

## Example:

```
@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

	Optional<Group> findByGroupName(String name) throws SQLException;
}
```

## Important:

Your database structure should not be changed

Your tests should work with the new dao with minimal changes

<br>
</details>
