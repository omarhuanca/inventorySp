package bo.umss.app.inventorySp.referral.model;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import bo.umss.app.inventorySp.StockTransaction;
import bo.umss.app.inventorySp.TestObjectBucket;
import bo.umss.app.inventorySp.business.product.model.Product;
import bo.umss.app.inventorySp.business.referral.model.StockReferral;

public class StockReferralTest {

	private Product potentialProduct;
	private LocalDate date;
	private final TestObjectBucket testObjectBucket = new TestObjectBucket();

	@BeforeEach
	public void setUp() {
		potentialProduct = testObjectBucket.createPlate();
		date = testObjectBucket.createDate();
	}

	@Test
	public void canNotBeNullCodeProduct() {
		assertThrows(RuntimeException.class, () -> StockReferral.at(null, 1, date),
				StockTransaction.PRODUCT_CAN_NOT_BE_NULL);
	}

	@Test
	public void canNotBeLessThanZeroAmount() {
		assertThrows(RuntimeException.class, () -> StockReferral.at(potentialProduct, -1, date),
				StockTransaction.AMOUNT_CAN_NOT_BE_LESS_THAN_ZERO);
	}

	@Test
	public void canNotBeNullDate() {
		assertThrows(RuntimeException.class, () -> StockReferral.at(potentialProduct, 5, null),
				StockTransaction.DATE_CAN_NOT_BE_NULL);
	}
}
