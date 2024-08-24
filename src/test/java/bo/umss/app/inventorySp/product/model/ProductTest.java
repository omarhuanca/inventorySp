package bo.umss.app.inventorySp.product.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import bo.umss.app.inventorySp.TestObjectBucket;
import bo.umss.app.inventorySp.business.buy.model.StockBuy;
import bo.umss.app.inventorySp.business.codeProduct.model.NotProvidedProvider;
import bo.umss.app.inventorySp.business.coin.model.Coin;
import bo.umss.app.inventorySp.business.line.model.Line;
import bo.umss.app.inventorySp.business.measurement.model.Measurement;
import bo.umss.app.inventorySp.business.price.model.Price;
import bo.umss.app.inventorySp.business.product.model.Product;
import bo.umss.app.inventorySp.business.referral.model.StockReferral;
import bo.umss.app.inventorySp.business.stock.model.Stock;

public class ProductTest {

	private NotProvidedProvider notProvidedProvider;
	private Coin coin;
	private Measurement measurement;
	private Line line;
	private Price priceCost;
	private Price priceSale;
	private Stock stock;
	private Product plate;
	private LocalDate date;

	@BeforeEach
	public void setUp() {
		line = Line.at(TestObjectBucket.PLATE_NAME);
		notProvidedProvider = NotProvidedProvider.at(TestObjectBucket.BOWL8_CODE, TestObjectBucket.BOWL8_DESCRIPTION,
				line);
		coin = Coin.at(TestObjectBucket.CODE_USA);
		priceCost = Price.at("PR-1", 5.0, coin);
		priceSale = Price.at("PR-1", 10.0, coin);
		measurement = Measurement.at(TestObjectBucket.CODE_PZA);
		stock = Stock.at("ST-1", 10, measurement);

		plate = Product.at(notProvidedProvider, stock, priceCost, priceSale);

		date = LocalDate.of(2024, 05, 30);
	}

	@Test
	public void notLetInvalidStock() {
		Price priceCost2 = Price.at("PR-1", 5.0, coin);
		Price priceSale2 = Price.at("PR-1", 6.0, coin);

		assertThrows(RuntimeException.class, () -> Product.at(notProvidedProvider, null, priceCost2, priceSale2),
				Product.STOCK_CAN_NOT_BE_NULL);
	}

	@Test
	public void notLetPriceCostBeNull() {
		Price priceSale2 = Price.at("PR-1", 6.0, coin);
		Stock stock2 = Stock.at("ST-1", 1, measurement);

		assertThrows(RuntimeException.class, () -> Product.at(notProvidedProvider, stock2, null, priceSale2),
				Product.PRICE_COST_CAN_NOT_BE_NULL);
	}

	@Test
	public void notLetPriceSaleBeNull() {
		Price priceCost2 = Price.at("PR-1", 5.0, coin);
		Stock stock2 = Stock.at("ST-1", 1, measurement);

		assertThrows(RuntimeException.class, () -> Product.at(notProvidedProvider, stock2, priceCost2, null),
				Product.PRICE_SALE_CAN_NOT_BE_NULL);
	}

	@Test
	public void notLetNoExistCodeProduct() {
		Stock stock2 = Stock.at("ST-1", 1, measurement);
		Product plate = Product.at(notProvidedProvider, stock2, priceCost, priceSale);

		assertTrue(plate.alreadyCodeProduct());
	}

	@Test
	public void notLetAnyItemOfListTransaction() {
		Stock stock2 = Stock.at("ST-1", 1, measurement);
		Product plate = Product.at(notProvidedProvider, stock2, priceCost, priceSale);

		assertFalse(plate.listTransactionCompareGreatherThanZero(0));
	}

	@Test
	public void changeSizeListChangePriceAfterIncreasePriceCost() {
		Product plate = Product.at(notProvidedProvider, stock, priceCost, priceSale);

		Price priceCostOther = Price.at("PR-1", 4.0, coin);
		plate.changePriceBuy(priceCostOther, plate.getStock());

		assertEquals(0, plate.getListChangePriceCost().size());
	}

	@Test
	public void changePriceCostAfterIncreaseValueTwoTimes() {
		Product plate = Product.at(notProvidedProvider, stock, priceCost, priceSale);

		Price priceCostOther1 = Price.at("PR-1", 8.0, coin);
		plate.changePriceBuy(priceCostOther1, plate.getStock());
		Price priceCostOther2 = Price.at("PR-1", 4.0, coin);
		plate.changePriceBuy(priceCostOther2, plate.getStock());

		assertEquals(1, plate.getListChangePriceCost().size());
	}

	@Test
	public void addDiffTypeCoinAfterChangeValuePriceCost() {
		Product plate = Product.at(notProvidedProvider, stock, priceCost, priceSale);

		Coin coin2 = Coin.at(TestObjectBucket.CODE_BS);
		Price priceCostOther1 = Price.at("PR-1", 8.0, coin2);
		plate.changePriceBuy(priceCostOther1, plate.getStock());

		assertEquals(0, plate.getListChangePriceCost().size());
	}

	@Test
	public void verifySizeListAfterAddTransactionBuy() {
		Product plate = Product.at(notProvidedProvider, stock, priceCost, priceSale);

		StockBuy buy = StockBuy.at(notProvidedProvider, 5, date, TestObjectBucket.PLATE_PURCHEASE_DESCRIPTION);
		plate.addBuy(buy);

		assertTrue(plate.listTransactionCompareGreatherThanZero(0));
	}

	@Test
	public void verifyAddBuySuccess() {
		StockBuy buy = StockBuy.at(notProvidedProvider, 5, date, TestObjectBucket.PLATE_PURCHEASE_DESCRIPTION);
		plate.addBuy(buy);

		assertTrue(plate.getStock().compareOtherValue(15));
	}

	@Test
	public void verifyStockAfterAddReferral() {
		StockReferral referral = StockReferral.at(plate.getCodeProduct(), 5, date);
		plate.addReferral(referral);

		assertTrue(plate.getStock().compareOtherValue(5));
		assertEquals(1, plate.getListTransaction().size());
	}

	@Test
	public void toDoReferralAmountProduct() {
		// The amount referral should be greater than stock value
		StockReferral referral = StockReferral.at(plate.getCodeProduct(), 15, date);

		assertThrows(RuntimeException.class, () -> plate.addReferral(referral), Stock.AMOUNT_GREATER_THAN_AVAILABLE);
		assertEquals(0, plate.getListTransaction().size());
	}

	@Test
	public void toDoReferralAmountLessThanValueStock() {
		// The amount referral should be grether than stock value
		StockReferral referral = StockReferral.at(plate.getCodeProduct(), 8, date);
		plate.addReferral(referral);

		assertEquals(1, plate.getListTransaction().size());
	}
}
