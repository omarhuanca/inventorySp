package bo.umss.app.inventorySp.price.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import bo.umss.app.inventorySp.TestObjectBucket;
import bo.umss.app.inventorySp.business.coin.model.Coin;
import bo.umss.app.inventorySp.business.discount.model.Discount;
import bo.umss.app.inventorySp.business.price.model.Price;

public class PriceTest {

	private Coin coin;

	@BeforeEach
	public void setUp() {
		coin = Coin.at(TestObjectBucket.CODE_USA);
	}

	@Test
	public void canNotBeLessThanZeroValue() {
		assertThrows(RuntimeException.class, () -> Price.at(-3.0, coin), Price.VALUE_CAN_NOT_BE_LESS_ZERO);
	}

	@Test
	public void canNotEqualZeroValue() {
		assertThrows(RuntimeException.class, () -> Price.at(0.0, coin), Price.VALUE_CAN_NOT_BE_LESS_ZERO);
	}

	@Test
	public void verifyValue() {
		Price price = Price.at(2.5, coin);

		assertTrue(price.compareValueLessThanPotentialValue(3));
	}

	@Test
	public void verifySendValue() {
		Price price = Price.at(2.5, coin);

		assertTrue(price.compareValue(2.5));
	}

	@Test
	public void addTwoPriceSale() {
		Price priceOne = Price.at(5.5, coin);
		Price priceTwo = Price.at(3.5, coin);

		assertEquals(9.0, priceOne.addWithOtherPrice(priceTwo));
	}

	@Test
	public void verifyZeroDiscount() {
		Price total = Price.at(50.0, coin);
		Discount discount = Discount.at(0);

		assertTrue(total.applyDiscount(discount).compareValue(50.0));
	}

	@Test
	public void verifyOperationDiscount() {
		Price total = Price.at(50.0, coin);
		Discount discount = Discount.at(20);

		assertTrue(total.applyDiscount(discount).compareValue(30.0));
	}
}
