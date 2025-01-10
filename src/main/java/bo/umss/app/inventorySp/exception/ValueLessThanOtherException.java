package bo.umss.app.inventorySp.exception;

public class ValueLessThanOtherException extends RuntimeException {

	private static final long serialVersionUID = -4042870852639521954L;

	public ValueLessThanOtherException() {
		super();
	}

	public ValueLessThanOtherException(String message) {
		super(message);
	}
}
