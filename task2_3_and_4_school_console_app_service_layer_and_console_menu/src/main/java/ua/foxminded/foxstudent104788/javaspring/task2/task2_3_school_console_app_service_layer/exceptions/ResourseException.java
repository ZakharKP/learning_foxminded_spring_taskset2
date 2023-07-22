package ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.exceptions;

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