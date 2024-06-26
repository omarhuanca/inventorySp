package bo.umss.app.inventorySp.exception;

public class CrudException extends RuntimeException {

	private static final long serialVersionUID = 2516826373496226965L;
	public static final String DATA_ACCESS = "Connection error";

	private String message;

	public CrudException(String message) {
		super(message);
		this.message = message;
	}

	public CrudException() {
		super();
	}

	public String getMessage() {
		return message;
	}
}
