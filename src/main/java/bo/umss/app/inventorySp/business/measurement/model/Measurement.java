package bo.umss.app.inventorySp.business.measurement.model;

public class Measurement {

	public static final String CODE_CAN_NOT_BE_BLANK = "Code can not be blank";

	private String code;

	public Measurement(String code) {
		this.code = code;
	}

	public static Measurement at(String code) {
		if (code.isEmpty())
			throw new RuntimeException(CODE_CAN_NOT_BE_BLANK);

		return new Measurement(code);
	}

	public String getCode() {
		return code;
	}
}
