package bo.umss.app.inventorySp.business.provider.dto;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import bo.umss.app.inventorySp.business.provider.model.Provider;
import bo.umss.app.inventorySp.exception.EmptyFieldException;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProviderDto {

	@NotBlank
	private String name;

	@NotBlank
	private String phoneNumber;

	public ProviderDto(String name, String phoneNumber) {
		this.name = name;
		this.phoneNumber = phoneNumber;
	}

	public ProviderDto() {
	}

	public static ProviderDto at(@NotBlank String name, @NotBlank String phoneNumber) {
		if (name.isEmpty())
			throw new EmptyFieldException(Provider.NAME_CAN_NOT_BE_BLANK);
		if (phoneNumber.isEmpty())
			throw new EmptyFieldException(Provider.PHONE_NUMBER_CAN_NOT_BE_BLANK);
		return new ProviderDto(name, phoneNumber);
	}

	public String getName() {
		return name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}
}
