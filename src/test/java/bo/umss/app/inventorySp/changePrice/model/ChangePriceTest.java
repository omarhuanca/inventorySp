package bo.umss.app.inventorySp.changePrice.model;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import bo.umss.app.inventorySp.TestObjectBucket;
import bo.umss.app.inventorySp.business.changePrice.model.ChangePrice;
import bo.umss.app.inventorySp.business.coin.model.Coin;
import bo.umss.app.inventorySp.business.measurement.model.Measurement;

public class ChangePriceTest {

	private LocalDate currentDate;
	private Coin coin;
	private Integer stock;
	private Measurement measurement;

	@BeforeEach
	public void setUp() {
		currentDate = LocalDate.now();
		coin = Coin.at(TestObjectBucket.CODE_USD);
		measurement = Measurement.at(TestObjectBucket.CODE_PZA);
		stock = 2;
	}

	@Test
	public void canNotLetNewPriceBeNull() {
		Double oldPrice = 4.0;

		assertThrows(RuntimeException.class,
				() -> ChangePrice.at(null, oldPrice, coin, stock, measurement, currentDate),
				ChangePrice.NEW_PRICE_CAN_NOT_BE_NULL);
	}

	@Test
	public void canNotLetOldPriceBeNull() {
		Double newPrice = 5.0;

		assertThrows(RuntimeException.class,
				() -> ChangePrice.at(newPrice, null, coin, stock, measurement, currentDate),
				ChangePrice.OLD_PRICE_CAN_NOT_BE_NULL);
	}

	@Test
	public void canNotBeNullStock() {
		Double newPrice = 5.0;
		Double oldPrice = 10.0;

		assertThrows(RuntimeException.class,
				() -> ChangePrice.at(newPrice, oldPrice, coin, null, measurement, currentDate),
				ChangePrice.STOCK_CAN_NOT_BE_NULL);
	}

	@Test
	public void canNotBeNullCurrentDate() {
		Double newPrice = 5.0;
		Double oldPrice = 10.0;

		assertThrows(RuntimeException.class, () -> ChangePrice.at(newPrice, oldPrice, coin, stock, measurement, null),
				ChangePrice.CURRENT_DATE_CAN_NOT_BE_NULL);
	}

	@Test
	public void newPriceCanNotBeLessThanOldPrice() {
		Double newPrice = 5.0;
		Double oldPrice = 10.0;

		assertThrows(RuntimeException.class,
				() -> ChangePrice.at(newPrice, oldPrice, coin, stock, measurement, currentDate),
				ChangePrice.NEW_PRICE_CAN_NOT_LESS_THAN_OLD_PRICE);
	}
}
