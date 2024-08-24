package bo.umss.app.inventorySp.business.measurement.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import bo.umss.app.inventorySp.business.measurement.model.Measurement;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MeasurementDto {

	@NotBlank
	private String code;

	public MeasurementDto(String code) {
		this.code = code;
	}

	public MeasurementDto() {
	}

	public static MeasurementDto at(@NotNull String code) {
		if (code.isEmpty())
			throw new RuntimeException(Measurement.CODE_CAN_NOT_BE_BLANK);

		return new MeasurementDto(code);
	}

	public String getCode() {
		return code;
	}
}
