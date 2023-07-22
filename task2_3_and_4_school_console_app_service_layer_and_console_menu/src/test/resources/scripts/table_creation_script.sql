-- Drop tables if they already exist
DROP TABLE IF EXISTS course_rosters;
DROP TABLE IF EXISTS courses;
DROP TABLE IF EXISTS students;
DROP TABLE IF EXISTS groups;

-- Create tables
CREATE TABLE groups (
    group_id SERIAL PRIMARY KEY,
    group_name VARCHAR(255) NOT NULL
);

CREATE TABLE students (
    student_id SERIAL PRIMARY KEY,
    group_id INT,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    FOREIGN KEY (group_id) REFERENCES groups(group_id) ON DELETE SET NULL
);

CREATE TABLE courses (
    course_id SERIAL PRIMARY KEY,
    course_name VARCHAR(255),
    course_description VARCHAR(255)
);

CREATE TABLE course_rosters (
    course_roster_id SERIAL PRIMARY KEY,
    student_id INT,
    course_id INT,
    FOREIGN KEY (student_id) REFERENCES students(student_id) ON DELETE CASCADE,
    FOREIGN KEY (course_id) REFERENCES courses(course_id) ON DELETE CASCADE
);