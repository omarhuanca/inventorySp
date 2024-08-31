package bo.umss.app.inventorySp.business.line.dto;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import bo.umss.app.inventorySp.business.line.model.Line;
import bo.umss.app.inventorySp.exception.EmptyFieldException;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LineDto {

	@NotBlank
	private String name;

	public LineDto(String name) {
		this.name = name;
	}

	public LineDto() {
	}

	public static LineDto at(@NotBlank String name) {
		if (name.isEmpty())
			throw new EmptyFieldException(Line.NAME_CAN_NOT_BE_BLANK);

		return new LineDto(name);
	}

	public String getName() {
		return name;
	}
}
