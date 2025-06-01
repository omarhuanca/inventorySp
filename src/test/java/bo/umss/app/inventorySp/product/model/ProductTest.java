package bo.umss.app.inventorySp.product.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import bo.umss.app.inventorySp.TestObjectBucket;
import bo.umss.app.inventorySp.business.buy.model.StockBuy;
import bo.umss.app.inventorySp.business.coin.model.Coin;
import bo.umss.app.inventorySp.business.line.model.Line;
import bo.umss.app.inventorySp.business.measurement.model.Measurement;
import bo.umss.app.inventorySp.business.product.model.Product;
import bo.umss.app.inventorySp.business.provider.model.Provider;
import bo.umss.app.inventorySp.business.referral.model.StockReferral;
import bo.umss.app.inventorySp.exception.ValueLessThanOtherException;

public class ProductTest {

	private Coin coin;
	private Measurement measurement;
	private Line line;
	private Double priceCost;
	private Double priceSale;
	private Integer stock;
	private Product plate;
	private LocalDate date;
	private Provider provider;
	private TestObjectBucket testObjectBucket;

	@BeforeEach
	public void setUp() {
		testObjectBucket = new TestObjectBucket();
		line = Line.at(TestObjectBucket.PLATE_NAME);
		coin = Coin.at(TestObjectBucket.CODE_USD);
		priceCost = 5.0;
		priceSale = 10.0;
		measurement = Measurement.at(TestObjectBucket.CODE_PZA);
		stock = 10;
		provider = Provider.at(TestObjectBucket.JUAN_PEREZ_NAME, TestObjectBucket.JUAN_PEREZ_CELLPHONE);

		plate = Product.at(TestObjectBucket.BOWL8_CODE, TestObjectBucket.BOWL8_DESCRIPTION, stock, measurement,
				priceCost, priceSale, coin, line, provider);

		date = LocalDate.of(2024, 05, 30);
	}

	@Test
	public void notLetPriceCostBeNull() {
		Double priceSale2 = 6.0;
		Integer stock2 = 1;

		assertThrows(RuntimeException.class, () -> Product.at(TestObjectBucket.BOWL8_CODE,
				TestObjectBucket.BOWL8_DESCRIPTION, stock2, measurement, null, priceSale2, coin, line, provider),
				Product.PRICE_COST_CAN_NOT_BE_NULL);
	}

	@Test
	public void notLetPriceSaleBeNull() {
		Double priceCost2 = 5.0;
		Integer stock2 = 1;

		assertThrows(RuntimeException.class, () -> Product.at(TestObjectBucket.BOWL8_CODE,
				TestObjectBucket.BOWL8_DESCRIPTION, stock2, measurement, priceCost2, null, coin, line, provider),
				Product.PRICE_SALE_CAN_NOT_BE_NULL);
	}

	@Test
	public void notLetAnyItemOfListTransaction() {
		Integer stock2 = 1;
		Product anotherPlate = Product.at(TestObjectBucket.BOWL8_CODE, TestObjectBucket.BOWL8_DESCRIPTION, stock2,
				measurement, priceCost, priceSale, coin, line, provider);

		assertFalse(anotherPlate.listTransactionCompareGreatherThanZero(0));
	}

	@Test
	public void changeSizeListChangePriceAfterIncreasePriceCost() {
		Double priceCostOther = 4.0;
		plate.changePriceBuy(priceCostOther, plate.getStock());

		assertEquals(0, plate.getListChangePriceCost().size());
	}

	@Test
	public void changePriceCostAfterIncreaseValueTwoTimes() {
		Double priceCostOther1 = 8.0;
		plate.changePriceBuy(priceCostOther1, plate.getStock());
		Double priceCostOther2 = 4.0;
		plate.changePriceBuy(priceCostOther2, plate.getStock());

		assertEquals(1, plate.getListChangePriceCost().size());
	}

	@Test
	public void addDiffTypeCoinAfterChangeValuePriceCost() {
		Double priceCostOther1 = 8.0;
		plate.changePriceBuy(priceCostOther1, plate.getStock());

		assertEquals(1, plate.getListChangePriceCost().size());
	}

	@Test
	public void verifySizeListAfterAddTransactionBuy() {
		StockBuy buy = StockBuy.at(plate, 5, date, TestObjectBucket.PLATE_PURCHEASE_DESCRIPTION);
		plate.addBuy(buy);

		assertTrue(plate.listTransactionCompareGreatherThanZero(0));
	}

	@Test
	public void verifyAddBuySuccess() {
		StockBuy buy = StockBuy.at(plate, 5, date, TestObjectBucket.PLATE_PURCHEASE_DESCRIPTION);
		plate.addBuy(buy);

		assertEquals(plate.getStock(), 15);
	}

	@Test
	public void verifyStockAfterAddReferral() {
		StockReferral referral = StockReferral.at(plate, 5, date);
		plate.addReferral(referral);

		assertEquals(plate.getStock(), 5);
		assertEquals(1, plate.getListReferral().size());
	}

	@Test
	public void toDoReferralAmountProduct() {
		// The amount referral should be greater than stock value
		StockReferral referral = StockReferral.at(plate, 15, date);

		assertThrows(RuntimeException.class, () -> plate.addReferral(referral), Product.AMOUNT_GREATER_THAN_AVAILABLE);
		assertEquals(0, plate.getListReferral().size());
	}

	@Test
	public void toDoReferralAmountLessThanValueStock() {
		// The amount referral should be greater than stock value
		StockReferral referral = StockReferral.at(plate, 8, date);
		plate.addReferral(referral);

		assertEquals(1, plate.getListReferral().size());
	}

	@Test
	public void verifyCompareCodeSuccess() {
		plate.setCode("");

		assertTrue(plate.compareOtherCode(""));
	}

	@Test
	public void verifyCompareCodeWrong() {
		assertFalse(plate.compareOtherCode(""));
	}

	@Test
	public void verifyCompareDescriptionCorrect() {
		plate.setDescription("");

		assertTrue(plate.compareOtherDescription(""));
	}

	@Test
	public void verifyCompareDescriptionWrong() {
		assertFalse(plate.compareOtherDescription(""));
	}

	@Test
	public void verifyCompareStockCorrect() {
		assertTrue(plate.compareStock(stock));
	}

	@Test
	public void verifyCompareStockWrong() {
		Integer potentialStock = 20;

		assertNotEquals(plate, potentialStock);
	}

	@Test
	public void verifyComparePriceCostCorrect() {
		assertTrue(plate.comparePriceCost(priceCost));
	}

	@Test
	public void verifyComparePriceCostWrong() {
		Double potentialPriceCost = 7.0;

		assertNotEquals(plate, potentialPriceCost);
	}

	@Test
	public void verifyComparePriceSaleCorrect() {
		assertTrue(plate.comparePriceSale(priceSale));
	}

	@Test
	public void verifyComparePriceSaleWrong() {
		Double potentialPriceSale = 30.0;

		assertNotEquals(plate, potentialPriceSale);
	}

	@Test
	public void verifyCompareLineCorrect() {
		assertTrue(plate.compareLine(line));
	}

	@Test
	public void verifyCompareLineWrong() {
		Line potentialLine = testObjectBucket.createLinePlate();
		plate.setLine(potentialLine);

		assertFalse(plate.compareLine(line));
	}

	@Test
	public void verifyCompareProviderCorrect() {
		assertTrue(plate.compareProvider(provider));
	}

	@Test
	public void verifyCompareProviderWrong() {
		Provider potentialProvider = Provider.at(TestObjectBucket.JUAN_PEREZ_NAME + "a",
				TestObjectBucket.JUAN_PEREZ_CELLPHONE);
		plate.setProvider(potentialProvider);

		assertFalse(plate.compareProvider(provider));
	}

	@Test
	public void verifyPriceCostCanNotBeGreatherThanPriceSale() {
		assertThrows(ValueLessThanOtherException.class,
				() -> Product.at(TestObjectBucket.CUP_CODE, TestObjectBucket.CUP_PURCHASE_DESCRIPTION, stock,
						measurement, priceSale, priceCost, null, line, provider),
				Product.PRICE_SALE_CHEAPER_THAN_PRICE_COST);
	}
}
