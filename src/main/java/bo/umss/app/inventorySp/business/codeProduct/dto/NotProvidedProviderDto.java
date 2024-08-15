package bo.umss.app.inventorySp.business.codeProduct.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import bo.umss.app.inventorySp.business.codeProduct.model.NotProvidedProvider;
import bo.umss.app.inventorySp.business.line.dto.LineDto;
import bo.umss.app.inventorySp.exception.EmptyFieldException;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NotProvidedProviderDto {

	@NotBlank
	private String code;

	@NotBlank
	private String description;

	@NotNull
	private LineDto line;

	public NotProvidedProviderDto(String code, String description, LineDto line) {
		this.code = code;
		this.description = description;
		this.line = line;
	}

	public NotProvidedProviderDto() {
	}

	public static NotProvidedProviderDto at(@NotBlank String code, @NotBlank String description,
			@NotNull LineDto line) {
		if (code.isEmpty())
			throw new EmptyFieldException(NotProvidedProvider.CODE_CAN_NOT_BE_BLANK);
		if (description.isEmpty())
			throw new EmptyFieldException(NotProvidedProvider.DESCRIPTION_CAN_NOT_BE_BLANK);
		if (null == line)
			throw new EmptyFieldException(NotProvidedProvider.LINE_CAN_NOT_BE_NULL);

		return new NotProvidedProviderDto(code, description, line);
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public LineDto getLine() {
		return line;
	}
}
