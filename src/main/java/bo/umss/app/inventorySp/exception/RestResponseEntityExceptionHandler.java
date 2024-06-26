package bo.umss.app.inventorySp.exception;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	private Logger logger = LogManager.getLogger(getClass());

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest webRequest) {
		logger.warn("EntityNotFoundException.class -> ({}): {}.", webRequest.getDescription(false), ex.getMessage());
		return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(BadParamException.class)
	public ResponseEntity<Object> handleBadParamException(BadParamException ex, WebRequest request) {
		String message = ex.getLocalizedMessage() != null ? ex.getLocalizedMessage() : "";
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiErrorMessageDto(message));
	}

	@ExceptionHandler(UniqueViolationException.class)
	public ResponseEntity<ApiErrorMessageDto> handleUniqueViolationException(UniqueViolationException ex,
			WebRequest request) {
		Map<String, String> messageField = new HashMap<>();
		String message = ex.getLocalizedMessage() != null ? ex.getLocalizedMessage() : "";
		return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiErrorDto(message, messageField));

	}

	@ExceptionHandler(EmptyFieldException.class)
	public ResponseEntity<Object> handleEmptyFieldException(EmptyFieldException ex, WebRequest request) {
		return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}
}
