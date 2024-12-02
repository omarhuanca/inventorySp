package bo.umss.app.inventorySp.business.stock.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import bo.umss.app.inventorySp.business.measurement.dto.MeasurementDto;
import bo.umss.app.inventorySp.business.stock.model.Stock;
import bo.umss.app.inventorySp.exception.NegativeFieldException;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StockDto {

	@NotNull
	private Long id;

	@NotNull
	private Integer value;

	@NotNull
	private MeasurementDto measurement;

	public StockDto(Long id, Integer value, MeasurementDto measurement) {
		this.id = id;
		this.value = value;
		this.measurement = measurement;
	}

	public StockDto() {
	}

	public static StockDto at(Long id, @NotNull Integer value, @NotNull MeasurementDto measurement) {
		if (0 > value)
			throw new NegativeFieldException(Stock.VALUE_CAN_NOT_BE_LESS_THAN_ZERO);
		if (null == measurement)
			throw new RuntimeException(Stock.MEASUREMENT_CAN_NOT_BE_NULL);

		return new StockDto(id, value, measurement);
	}

	public Long getId() {
		return id;
	}

	public Integer getValue() {
		return value;
	}

	public MeasurementDto getMeasurement() {
		return measurement;
	}
}
