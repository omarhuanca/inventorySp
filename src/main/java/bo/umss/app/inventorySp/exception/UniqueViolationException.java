package bo.umss.app.inventorySp.exception;

public class UniqueViolationException extends RuntimeException {

	private static final long serialVersionUID = 8703708388881536983L;
	public static final String DATA_DUPLICATE = "Existing data";

	public UniqueViolationException(String message) {
		super(message);
	}
}
