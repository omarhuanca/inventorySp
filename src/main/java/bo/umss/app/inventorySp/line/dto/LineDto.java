package bo.umss.app.inventorySp.line.dto;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LineDto {

	@NotBlank
	private String name;

	public LineDto() {
	}

	public LineDto(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
