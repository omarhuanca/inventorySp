package bo.umss.app.inventorySp.business.stock.model;

import bo.umss.app.inventorySp.business.measurement.model.Measurement;

public class Stock {

	public static final String VALUE_CAN_NOT_BE_LESS_THAN_ZERO = "Value can not be less zero";
	public static final String MEASUREMENT_CAN_NOT_BE_NULL = "Measurement can not be null";
	public static final String AMOUNT_GREATER_THAN_AVAILABLE = "Amount greater than available";

	private Integer value;
	private Measurement measurement;

	public Stock(Integer value, Measurement measurement) {
		this.value = value;
		this.measurement = measurement;
	}

	public static Stock at(Integer value, Measurement measurement) {
		if (0 >= value)
			throw new RuntimeException(VALUE_CAN_NOT_BE_LESS_THAN_ZERO);
		if (null == measurement)
			throw new RuntimeException(MEASUREMENT_CAN_NOT_BE_NULL);

		return new Stock(value, measurement);
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public Measurement getMeasurement() {
		return measurement;
	}

	public Boolean amountGreaterThanZero() {
		return value > 0;
	}

	public Boolean verifyValueGreaterThanPotentialValue(Integer potentialValue) {
		return value >= potentialValue;
	}

	public void todoDecrementStock(Integer potentialStock) {
		if (verifyValueGreaterThanPotentialValue(potentialStock)) {
			setValue(value - potentialStock);
		} else {
			throw new RuntimeException(AMOUNT_GREATER_THAN_AVAILABLE);
		}
	}

	public Boolean compareValue(Integer potentialValue) {
		return value.equals(potentialValue);
	}

	public Boolean verifyPotentialValueGreaterZero(Integer potentialValue) {
		return potentialValue > 0;
	}

	public void todoIncreaseStock(Integer potentialValue) {
		if (value > 0) {
			value = value + potentialValue;
		}
	}
}
