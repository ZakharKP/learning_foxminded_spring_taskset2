# Task 2.3 Service layer

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

# Task 2.4 Console menu

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