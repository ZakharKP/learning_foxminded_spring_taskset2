package ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.lang.Nullable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * The Student class represents a student entity.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = Constants.STUDENT_TABLE_NAME)
//@ToString(of = { "id", "firstName", "lastName", "group" })
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = Constants.STUDENT_COLUMN_NAME_ID)
	private Integer id;

	@Column(name = Constants.STUDENT_COLUMN_NAME_FIRST_NAME)
	private String firstName;

	@Column(name = Constants.STUDENT_COLUMN_NAME_LAST_NAME)
	private String lastName;

	@Nullable
	@EqualsAndHashCode.Exclude
	@ManyToOne(targetEntity = Group.class)
	@JoinColumn(name = Constants.GROUP_COLUMN_NAME_ID)
	private Group group;

	@Builder.Default
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	@ManyToMany(fetch = FetchType.EAGER, targetEntity = Course.class)
	@JoinTable(name = Constants.COURSES_STUDENTS_TABLE_NAME
		, joinColumns = @JoinColumn(name = Constants.STUDENT_COLUMN_NAME_ID)
		, inverseJoinColumns = @JoinColumn(name = Constants.COURSE_COLUMN_NAME_ID))
	private Set<Course> courses = new HashSet<>();

}