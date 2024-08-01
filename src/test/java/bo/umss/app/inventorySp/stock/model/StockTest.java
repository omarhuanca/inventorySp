package bo.umss.app.inventorySp.stock.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import bo.umss.app.inventorySp.TestObjectBucket;
import bo.umss.app.inventorySp.business.measurement.model.Measurement;
import bo.umss.app.inventorySp.business.stock.model.Stock;

public class StockTest {

	private Measurement measurement;

	@BeforeEach
	public void setUp() {
		measurement = Measurement.at(TestObjectBucket.CODE_PZA);
	}

	@Test
	public void canNotBeLessThanZeroValue() {
		assertThrows(RuntimeException.class, () -> Stock.at(0, measurement), Stock.VALUE_CAN_NOT_BE_LESS_THAN_ZERO);
	}

	@Test
	public void canNotBeNullMeasurement() {
		assertThrows(RuntimeException.class, () -> Stock.at(5, null), Stock.MEASUREMENT_CAN_NOT_BE_NULL);
	}

	@Test
	public void verifyPotentialValueIsLessThan() {
		Stock stock = Stock.at(5, measurement);

		assertTrue(stock.verifyValueGreaterThanPotentialValue(2));
	}

	@Test
	public void verifyPotentialValueIsGreaterThan() {
		Stock stock = Stock.at(5, measurement);

		assertFalse(stock.verifyValueGreaterThanPotentialValue(7));
	}

	@Test
	public void verifyDecrementValue() {
		Stock stock = Stock.at(10, measurement);
		stock.todoDecrementStock(3);

		assertTrue(stock.compareValue(7));
	}
}

