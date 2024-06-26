package bo.umss.app.inventorySp.exception;

public class BadParamException extends RuntimeException {

	private static final long serialVersionUID = 9028291306910548157L;

	public BadParamException() {
	}

	public BadParamException(String message) {
		super(message);
	}
}
