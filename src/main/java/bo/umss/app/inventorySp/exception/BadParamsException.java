package bo.umss.app.inventorySp.exception;

public class BadParamsException extends RuntimeException {

	private static final long serialVersionUID = 9028291306910548157L;

	public BadParamsException() {
	}

	public BadParamsException(String message) {
		super(message);
	}
}
