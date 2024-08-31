package bo.umss.app.inventorySp.price.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
	private Price potentialPrice;

	@BeforeEach
	public void setUp() {
		coin = Coin.at(TestObjectBucket.CODE_USA);
		potentialPrice = Price.at("PR-1", 30.0, coin);
	}

	@Test
	public void canNotBeLessThanZeroValue() {
		assertThrows(RuntimeException.class, () -> Price.at("PR-1", -3.0, coin), Price.VALUE_CAN_NOT_BE_LESS_ZERO);
	}

	@Test
	public void canNotEqualZeroValue() {
		assertThrows(RuntimeException.class, () -> Price.at("PR-1", 0.0, coin), Price.VALUE_CAN_NOT_BE_LESS_ZERO);
	}

	@Test
	public void verifyValue() {
		assertTrue(potentialPrice.compareValueLessThanPotentialValue(31));
	}

	@Test
	public void verifySendValue() {
		assertTrue(potentialPrice.compareOtherValue(30.0));
	}

	@Test
	public void addTwoPriceSale() {
		Price priceTwo = Price.at("PR-2", 4.0, coin);

		assertEquals(34.0, potentialPrice.addWithOtherPrice(priceTwo));
	}

	@Test
	public void verifyZeroDiscount() {
		Discount discount = Discount.at(0);

		assertTrue(potentialPrice.applyDiscount(discount).compareOtherValue(30.0));
	}

	@Test
	public void verifyOperationDiscount() {
		Discount discount = Discount.at(20);

		assertTrue(potentialPrice.applyDiscount(discount).compareOtherValue(10.0));
	}

	@Test
	public void verifyValueDoesntNegative() {
		assertFalse(potentialPrice.isNegativeValue());
	}

	@Test
	public void verifyValueIsNegative() {
		potentialPrice.setValue(-8.0);

		assertTrue(potentialPrice.isNegativeValue());
	}

	@Test
	public void verifyWrongCompareCode() {
		Price anotherPrice = Price.at("PR-2", 50.0, coin);

		assertFalse(potentialPrice.compareAnotherCode(anotherPrice.getCode()));
	}

	@Test
	public void verifyCorrectCompareCode() {
		Price similarPrice = Price.at("PR-1", 20.0, coin);

		assertTrue(potentialPrice.compareAnotherCode(similarPrice.getCode()));
	}
}
