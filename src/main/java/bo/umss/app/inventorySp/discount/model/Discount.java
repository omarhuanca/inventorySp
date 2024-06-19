package bo.umss.app.inventorySp.discount.model;

public class Discount {

	public static final String VALUE_CAN_NOT_LESS_THAN_ZERO = "Value can not be less than zero or equal to zero";

	private Integer value;

	public Discount(Integer value) {
		this.value = value;
	}

	public static Discount at(Integer value) {
		if (value < 0)
			throw new RuntimeException(VALUE_CAN_NOT_LESS_THAN_ZERO);

		return new Discount(value);
	}

	public Integer getValue() {
		return value;
	}
}
