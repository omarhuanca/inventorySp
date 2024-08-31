package bo.umss.app.inventorySp.business.stock.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import bo.umss.app.inventorySp.business.measurement.model.Measurement;
import bo.umss.app.inventorySp.exception.EmptyFieldException;
import bo.umss.app.inventorySp.exception.NegativeFieldException;

@Entity
@Table(name = "st_stock", uniqueConstraints = { @UniqueConstraint(columnNames = { "st_code" }) })
public class Stock implements Serializable {

	private static final long serialVersionUID = 8389217286958445461L;

	public static final String CODE_CAN_NOT_BE_BLANK = "Code can not be blank";
	public static final String VALUE_CAN_NOT_BE_LESS_THAN_ZERO = "Value can not be less zero";
	public static final String MEASUREMENT_CAN_NOT_BE_NULL = "Measurement can not be null";
	public static final String AMOUNT_GREATER_THAN_AVAILABLE = "Amount greater than available";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@SequenceGenerator(name = "stc_stock", sequenceName = "st_seq", initialValue = 1000)
	@Column(name = "st_id")
	private Long id;

	@NotNull
	@Column(name = "st_code")
	private String code;

	@NotNull
	@Column(name = "st_value")
	private Integer value;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "st_ms_id", nullable = false)
	private Measurement measurement;

	public Stock(String code, Integer value, Measurement measurement) {
		this.code = code;
		this.value = value;
		this.measurement = measurement;
	}

	public Stock() {
	}

	public static Stock at(String code, Integer value, Measurement measurement) {
		if (code.isEmpty())
			throw new EmptyFieldException(CODE_CAN_NOT_BE_BLANK);
		if (0 > value)
			throw new NegativeFieldException(VALUE_CAN_NOT_BE_LESS_THAN_ZERO);
		if (null == measurement)
			throw new RuntimeException(MEASUREMENT_CAN_NOT_BE_NULL);

		return new Stock(code, value, measurement);
	}

	public String getCode() {
		return code;
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

	public Boolean compareOtherValue(Integer potentialValue) {
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

	public Boolean compareOtherCode(String potentialCode) {
		return code.equalsIgnoreCase(potentialCode);
	}

	public void setMeasurement(Measurement potentialMeasurement) {
		measurement = potentialMeasurement;
	}

	public Boolean verifyValueIsNegative() {
		return 0 > value;
	}
}
