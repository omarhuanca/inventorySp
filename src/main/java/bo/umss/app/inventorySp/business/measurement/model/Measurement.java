package bo.umss.app.inventorySp.business.measurement.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "ms_measurement", uniqueConstraints = { @UniqueConstraint(columnNames = { "ms_code" }) })
public class Measurement implements Serializable {

	private static final long serialVersionUID = 2181430776512445072L;

	public static final String CODE_CAN_NOT_BE_BLANK = "Code can not be blank";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@SequenceGenerator(name = "meas_measurement", sequenceName = "ms_seq", initialValue = 1000)
	@Column(name = "ms_id")
	private Long id;

	@NotNull
	@Column(name = "ms_code")
	private String code;

	public Measurement(String code) {
		this.code = code;
	}

	public Measurement() {
	}

	public static Measurement at(String code) {
		if (code.isEmpty())
			throw new RuntimeException(CODE_CAN_NOT_BE_BLANK);

		return new Measurement(code);
	}

	public String getCode() {
		return code;
	}

	public boolean compareOtherCode(String potentialCode) {
		return code.equalsIgnoreCase(potentialCode);
	}

	public void setCode(String potentialCode) {
		code = potentialCode;
	}
}
