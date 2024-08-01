package bo.umss.app.inventorySp.business.line.dto;

import javax.validation.constraints.NotBlank;

import bo.umss.app.inventorySp.business.line.model.Line;
import bo.umss.app.inventorySp.exception.EmptyFieldException;

public class LineDto {

	private Long id;
	
	@NotBlank
	private String name;

	public LineDto(String name) {
		this.name = name;
	}

	public LineDto() {
		super();
	}

	public static LineDto at(@NotBlank String name) {
		if (name.isEmpty())
			throw new EmptyFieldException(Line.NAME_CAN_NOT_BE_BLANK);

		return new LineDto(name);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
}
