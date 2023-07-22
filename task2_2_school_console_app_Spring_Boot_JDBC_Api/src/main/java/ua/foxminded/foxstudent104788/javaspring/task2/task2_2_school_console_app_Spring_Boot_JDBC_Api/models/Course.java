package ua.foxminded.foxstudent104788.javaspring.task2.task2_2_school_console_app_Spring_Boot_JDBC_Api.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The Course class represents a course entity.
 */
@AllArgsConstructor
@Getter
public class Course {
	private Integer id;
	private String courseName;
	private String courseDescription;

	/**
	 * Compares this Course object to another object for equality.
	 *
	 * @param obj the object to compare to
	 * @return true if the objects are equal, false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}

		Course other = (Course) obj;

		if (id != null && id == other.getId()) {
			return true;
		}

		return courseName.equals(other.getCourseName()) && courseDescription.equals(other.getCourseDescription());

	}
}
