package bo.umss.app.inventorySp.business.stock.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import bo.umss.app.inventorySp.business.measurement.dto.MeasurementDto;
import bo.umss.app.inventorySp.business.stock.model.Stock;
import bo.umss.app.inventorySp.exception.EmptyFieldException;
import bo.umss.app.inventorySp.exception.NegativeFieldException;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StockDto {

	@NotNull
	private String code;

	@NotNull
	private Integer value;

	@NotNull
	private MeasurementDto measurement;

	public StockDto(String code, Integer value, MeasurementDto measurement) {
		this.code = code;
		this.value = value;
		this.measurement = measurement;
	}

	public StockDto() {
	}

	public static StockDto at(@NotNull String code, @NotNull Integer value, @NotNull MeasurementDto measurement) {
		if (code.isEmpty())
			throw new EmptyFieldException(Stock.CODE_CAN_NOT_BE_BLANK);
		if (0 > value)
			throw new NegativeFieldException(Stock.VALUE_CAN_NOT_BE_LESS_THAN_ZERO);
		if (null == measurement)
			throw new RuntimeException(Stock.MEASUREMENT_CAN_NOT_BE_NULL);

		return new StockDto(code, value, measurement);
	}

	public String getCode() {
		return code;
	}

	public Integer getValue() {
		return value;
	}

	public MeasurementDto getMeasurement() {
		return measurement;
	}
}
