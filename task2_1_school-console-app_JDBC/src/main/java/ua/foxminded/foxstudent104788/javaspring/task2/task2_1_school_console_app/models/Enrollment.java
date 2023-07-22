package ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The Enrollment class represents an enrollment entity.
 */
@AllArgsConstructor
@Getter
public class Enrollment {

	private Integer id;
	private Integer studentId;
	private Integer courseId;

	/**
	 * Compares this enrollment with the specified object for equality.
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

		Enrollment other = (Enrollment) obj;

		if (id != null && id == other.getId()) {
			return true;
		}

		return studentId == other.getStudentId() && courseId == other.getCourseId();

	}

}
