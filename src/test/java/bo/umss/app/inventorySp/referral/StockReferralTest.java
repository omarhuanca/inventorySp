package bo.umss.app.inventorySp.referral;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import bo.umss.app.inventorySp.StockTransaction;
import bo.umss.app.inventorySp.TestObjectBucket;
import bo.umss.app.inventorySp.codeProduct.CodeProduct;
import bo.umss.app.inventorySp.codeProduct.model.NotProvidedProvider;
import bo.umss.app.inventorySp.line.model.Line;
import bo.umss.app.inventorySp.referral.model.StockReferral;

public class StockReferralTest {

	private CodeProduct notProvidedProvider;
	private LocalDate date;
	private final TestObjectBucket testObjectBucket = new TestObjectBucket();

	@BeforeEach
	public void setUp() {
		Line line = Line.at(TestObjectBucket.PLATE_NAME);
		notProvidedProvider = NotProvidedProvider.at(TestObjectBucket.BOWL8_CODE, TestObjectBucket.BOWL8_DESCRIPTION, line);
		date = testObjectBucket.createDate();
	}

	@Test
	public void canNotBeNullCodeProduct() {
		assertThrows(RuntimeException.class, () -> StockReferral.at(null, 1, date),
				StockTransaction.CODE_PRODUCT_CAN_NOT_BE_NULL);
	}

	@Test
	public void canNotBeLessThanZeroAmount() {
		assertThrows(RuntimeException.class, () -> StockReferral.at(notProvidedProvider, -1, date),
				StockTransaction.AMOUNT_CAN_NOT_BE_LESS_THAN_ZERO);
	}

	@Test
	public void canNotBeNullDate() {
		assertThrows(RuntimeException.class, () -> StockReferral.at(notProvidedProvider, 5, null),
				StockTransaction.DATE_CAN_NOT_BE_NULL);
	}
}
