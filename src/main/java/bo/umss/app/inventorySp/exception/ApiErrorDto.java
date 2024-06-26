package bo.umss.app.inventorySp.exception;

import java.util.Map;

public class ApiErrorDto extends ApiErrorMessageDto {

	private Map<String, String> errors;

	public ApiErrorDto(String message, Map<String, String> errors) {
		super(message);
		this.errors = errors;
	}

	public Map<String, String> getErrors() {
		return errors;
	}
}
