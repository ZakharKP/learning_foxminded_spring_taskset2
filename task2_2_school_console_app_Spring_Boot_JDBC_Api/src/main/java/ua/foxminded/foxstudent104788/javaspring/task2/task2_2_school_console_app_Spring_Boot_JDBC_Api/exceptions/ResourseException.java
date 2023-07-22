package ua.foxminded.foxstudent104788.javaspring.task2.task2_2_school_console_app_Spring_Boot_JDBC_Api.exceptions;

/**
 * The ResourseException class is a custom runtime exception that is thrown when
 * an exception occurs during resource handling.
 */
public class ResourseException extends RuntimeException {

	/**
	 * Constructs a new ResourseException with the specified cause.
	 *
	 * @param e The cause of the exception.
	 */
	public ResourseException(Exception e) {
		super(e);
	}

}