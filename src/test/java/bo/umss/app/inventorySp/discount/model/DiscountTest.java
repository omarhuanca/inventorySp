package bo.umss.app.inventorySp.discount.model;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import bo.umss.app.inventorySp.business.discount.model.Discount;

public class DiscountTest {

	@Test
	public void canNotBeLessThanZeroValue() {
		assertThrows(RuntimeException.class, () -> Discount.at(-3), Discount.VALUE_CAN_NOT_LESS_THAN_ZERO);
	}

	@Test
	public void canNotBeValueLessThanZero() {
		assertThrows(RuntimeException.class, () -> Discount.at(-15), Discount.VALUE_CAN_NOT_LESS_THAN_ZERO);
	}
}
