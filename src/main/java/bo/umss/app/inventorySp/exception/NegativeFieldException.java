package bo.umss.app.inventorySp.exception;

public class NegativeFieldException extends RuntimeException {

	private static final long serialVersionUID = -2865361891266023449L;

	public NegativeFieldException() {
		super();
	}

	public NegativeFieldException(String message) {
		super(message);
	}
}
