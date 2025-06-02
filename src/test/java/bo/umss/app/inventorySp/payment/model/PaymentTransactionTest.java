package bo.umss.app.inventorySp.payment.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import bo.umss.app.inventorySp.NoteTransaction;
import bo.umss.app.inventorySp.TestObjectBucket;
import bo.umss.app.inventorySp.business.coin.model.Coin;
import bo.umss.app.inventorySp.business.payment.model.PaymentTransaction;
import bo.umss.app.inventorySp.business.product.model.Product;
import bo.umss.app.inventorySp.business.referral.model.NoteReferral;
import bo.umss.app.inventorySp.business.sale.model.NoteSale;

public class PaymentTransactionTest {

	private Double total;
	private Integer discount;
	private Coin coin;
	private NoteTransaction noteReferral;
	private NoteSale noteSale;
	private final TestObjectBucket testObjectBucket = new TestObjectBucket();

	@BeforeEach
	public void setUp() {
		coin = Coin.at(TestObjectBucket.CODE_USD);
		total = 10.0;
		discount = 0;
		LocalDate date = testObjectBucket.createDate();
		noteReferral = NoteReferral.at(date);

		noteSale = NoteSale.at(date, TestObjectBucket.JUAN_PEREZ_NAME, TestObjectBucket.JUAN_PEREZ_NIT,
				TestObjectBucket.INVOICE_NUMBER);
	}

	@Test
	public void canNotBeNullTotal() {
		assertThrows(RuntimeException.class, () -> PaymentTransaction.at(null, discount, coin, noteReferral),
				PaymentTransaction.TOTAL_CAN_NOT_BE_NULL);
	}

	@Test
	public void canNotBeNullDiscount() {
		assertThrows(RuntimeException.class, () -> PaymentTransaction.at(total, null, coin, noteReferral),
				PaymentTransaction.DISCOUNT_CAN_NOT_BE_NULL);
	}

	@Test
	public void canNotBeNullNoteReferral() {
		assertThrows(RuntimeException.class, () -> PaymentTransaction.at(total, discount, coin, null),
				PaymentTransaction.NOTE_TRANSACTION_CAN_NOT_BE_NULL);
	}

	@Test
	public void verifyListProductIsGreaterThanZero() {
		assertThrows(RuntimeException.class, () -> PaymentTransaction.at(total, discount, coin, noteReferral),
				PaymentTransaction.PRODUCT_SIZE_GREATER_THAN);
	}

	@Test
	public void verifyNotTotalValueLessThanDiscountValue() {
		Integer discount2 = 15;

		assertThrows(RuntimeException.class, () -> PaymentTransaction.at(total, discount2, coin, noteReferral),
				PaymentTransaction.TOTAL_VALUE_LESS_THAN_DISCOUNT_VALUE);
	}

	@Test
	public void todoSingleProductSingleReferral() {
		Product plate = testObjectBucket.createPlate();
		noteReferral.addProduct(plate, 5);
		Map<String, Double> partTotal = noteReferral.calculateTotal();
		Double priceTotal = partTotal.get(TestObjectBucket.CODE_BS);

		assertEquals(priceTotal, 50.0);
	}

	@Test
	public void todoSingleProductTwoReferral() {
		Product plate = testObjectBucket.createPlate();
		noteReferral.addProduct(plate, 5);
		noteReferral.addProduct(plate, 3);
		Map<String, Double> partTotal = noteReferral.calculateTotal();
		Double priceTotal = partTotal.get(TestObjectBucket.CODE_BS);

		assertEquals(priceTotal, 20.0);

	}

	@Test
	public void verifyApplyDiscountSingleProduct() {
		Product plate = testObjectBucket.createPlate();
		noteReferral.addProduct(plate, 5);
		Map<String, Double> partTotal = noteReferral.calculateTotal();
		Double priceTotal = partTotal.get(TestObjectBucket.CODE_BS);
		Integer discountTwo = 10;
		PaymentTransaction paymentTransaction = PaymentTransaction.at(priceTotal, discountTwo, coin, noteReferral);
		Double amountToPay = paymentTransaction.generateAmountToPay();

		assertEquals(amountToPay, 40.0);
	}

	@Test
	public void verifyApplyDiscountForTwoProduct() {
		Product plate = testObjectBucket.createPlate();
		noteReferral.addProduct(plate, 3);
		Product cup = testObjectBucket.createCup();
		noteReferral.addProduct(cup, 6);

		Map<String, Double> partTotal = noteReferral.calculateTotal();
		Double priceTotal = partTotal.get(TestObjectBucket.CODE_BS);
		Integer discountTwo = 30;
		PaymentTransaction paymentTransaction = PaymentTransaction.at(priceTotal, discountTwo, coin, noteReferral);
		Double amountToPay = paymentTransaction.generateAmountToPay();

		assertEquals(amountToPay, 104.0);
	}

	@Test
	public void todoSingleProductPotSingleReferral() {
		Product pot = testObjectBucket.createPot();
		noteReferral.addProduct(pot, 10);
		Map<String, Double> partTotal = noteReferral.calculateTotal();
		Double priceTotal = partTotal.get(TestObjectBucket.CODE_USD);

		assertEquals(priceTotal, 17220.0);
	}

	@Test
	public void verifySummarizeTotalBs() {
		Product plate = testObjectBucket.createPlate();
		noteReferral.addProduct(plate, 5);
		// plate -> 50
		Product pot = testObjectBucket.createPot();
		noteReferral.addProduct(pot, 10);
		// pot -> 17220
		Map<String, Double> partTotal = noteReferral.calculateTotal();
		Double priceTotal = partTotal.get(TestObjectBucket.CODE_BS);

		assertEquals(priceTotal, 50.0);
	}

	@Test
	public void verifySummarizeTotalUSD() {
		Product plate = testObjectBucket.createPlate();
		noteReferral.addProduct(plate, 5);
		// plate -> 50
		Product pot = testObjectBucket.createPot();
		noteReferral.addProduct(pot, 10);
		// pot -> 17220
		Map<String, Double> partTotal = noteReferral.calculateTotal();
		Double priceTotal = partTotal.get(TestObjectBucket.CODE_USD);

		assertEquals(priceTotal, 17220.0);
	}

	@Test
	public void verifyAmountToPaySingleProduct() {
		Product plate = testObjectBucket.createPlate();
		noteSale.addProduct(plate, 3);
		Map<String, Double> partTotal = noteSale.calculateTotal();
		Double priceTotal = partTotal.get(TestObjectBucket.CODE_BS);

		assertEquals(priceTotal, 70.0);
	}
}
