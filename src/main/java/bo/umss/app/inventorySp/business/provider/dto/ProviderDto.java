package bo.umss.app.inventorySp.business.provider.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import bo.umss.app.inventorySp.business.provider.model.Provider;
import bo.umss.app.inventorySp.exception.EmptyFieldException;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProviderDto {

	@NotNull
	private Long id;

	@NotBlank
	private String name;

	public ProviderDto(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public ProviderDto() {
	}

	public static ProviderDto at(Long id, @NotBlank String name) {
		if (name.isEmpty())
			throw new EmptyFieldException(Provider.NAME_CAN_NOT_BE_BLANK);
		return new ProviderDto(id, name);
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}
