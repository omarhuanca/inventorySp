package bo.umss.app.inventorySp.business.line.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import bo.umss.app.inventorySp.business.line.model.Line;
import bo.umss.app.inventorySp.exception.EmptyFieldException;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LineDto {

	@NotNull
	private Long id;

	@NotBlank
	private String name;

	public LineDto(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public LineDto() {
	}

	public static LineDto at(Long id, @NotBlank String name) {
		if (name.isEmpty())
			throw new EmptyFieldException(Line.NAME_CAN_NOT_BE_BLANK);

		return new LineDto(id, name);
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}
