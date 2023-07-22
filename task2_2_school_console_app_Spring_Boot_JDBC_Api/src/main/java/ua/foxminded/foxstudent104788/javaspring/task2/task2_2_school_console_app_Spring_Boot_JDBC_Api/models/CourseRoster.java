package ua.foxminded.foxstudent104788.javaspring.task2.task2_2_school_console_app_Spring_Boot_JDBC_Api.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The CourseRoster class represents an course roster entity.
 */
@AllArgsConstructor
@Getter
public class CourseRoster {

	private Integer id;
	private Integer studentId;
	private Integer courseId;

	/**
	 * Compares this Course Roster with the specified object for equality.
	 *
	 * @param obj The object to compare to.
	 * @return {@code true} if the specified object is equal to this enrollment,
	 *         {@code false} otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}

		CourseRoster other = (CourseRoster) obj;

		if (id != null && id == other.getId()) {
			return true;
		}

		return studentId == other.getStudentId() && courseId == other.getCourseId();

	}

}
