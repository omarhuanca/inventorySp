package bo.umss.app.inventorySp.payment;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import bo.umss.app.inventorySp.NoteTransaction;
import bo.umss.app.inventorySp.TestObjectBucket;
import bo.umss.app.inventorySp.coin.model.Coin;
import bo.umss.app.inventorySp.discount.model.Discount;
import bo.umss.app.inventorySp.payment.model.PaymentTransaction;
import bo.umss.app.inventorySp.price.model.Price;
import bo.umss.app.inventorySp.product.model.Product;
import bo.umss.app.inventorySp.referral.model.NoteReferral;
import bo.umss.app.inventorySp.sale.model.NoteSale;

public class PaymentTransactionTest {

	private Price total;
	private Discount discount;
	private NoteTransaction noteReferral;
	private NoteSale noteSale;
	private final TestObjectBucket testObjectBucket = new TestObjectBucket();

	@BeforeEach
	public void setUp() {
		Coin coin = Coin.at(TestObjectBucket.CODE_USA);
		total = Price.at(10.0, coin);
		discount = Discount.at(0);
		LocalDate date = testObjectBucket.createDate();
		noteReferral = NoteReferral.at(date);

		noteSale = NoteSale.at(date, TestObjectBucket.JUAN_PEREZ_NAME, TestObjectBucket.JUAN_PEREZ_NIT,
				TestObjectBucket.INVOICE_NUMBER);
	}

	@Test
	public void canNotBeNullTotal() {
		assertThrows(RuntimeException.class, () -> PaymentTransaction.at(null, discount, noteReferral),
				PaymentTransaction.TOTAL_CAN_NOT_BE_NULL);
	}

	@Test
	public void canNotBeNullDiscount() {
		assertThrows(RuntimeException.class, () -> PaymentTransaction.at(total, null, noteReferral),
				PaymentTransaction.DISCOUNT_CAN_NOT_BE_NULL);
	}

	@Test
	public void canNotBeNullNoteReferral() {
		assertThrows(RuntimeException.class, () -> PaymentTransaction.at(total, discount, null),
				PaymentTransaction.NOTE_TRANSACTION_CAN_NOT_BE_NULL);
	}

	@Test
	public void verifyListProductIsGreaterThanZero() {
		assertThrows(RuntimeException.class, () -> PaymentTransaction.at(total, discount, noteReferral),
				PaymentTransaction.PRODUCT_SIZE_GREATER_THAN);
	}

	@Test
	public void verifyNotTotalValueLessThanDiscountValue() {
		Discount discount2 = Discount.at(15);
		assertThrows(RuntimeException.class, () -> PaymentTransaction.at(total, discount2, noteReferral),
				PaymentTransaction.TOTAL_VALUE_LESS_THAN_DISCOUNT_VALUE);
	}

	@Test
	public void todoSingleProductSingleReferral() {
		Product plate = testObjectBucket.createPlate();
		noteReferral.addProduct(plate, 5);
		Map<String, Price> partTotal = noteReferral.calculateTotal();
		Price priceTotal = partTotal.get(TestObjectBucket.CODE_BS);

		assertTrue(priceTotal.compareValue(50.0));
	}

	@Test
	public void todoSingleProductTwoReferral() {
		Product plate = testObjectBucket.createPlate();
		noteReferral.addProduct(plate, 5);
		noteReferral.addProduct(plate, 3);
		Map<String, Price> partTotal = noteReferral.calculateTotal();
		Price priceTotal = partTotal.get(TestObjectBucket.CODE_BS);

		assertTrue(priceTotal.compareValue(20.0));

	}

	@Test
	public void verifyApplyDiscountSingleProduct() {
		Product plate = testObjectBucket.createPlate();
		noteReferral.addProduct(plate, 5);
		Map<String, Price> partTotal = noteReferral.calculateTotal();
		Price priceTotal = partTotal.get(TestObjectBucket.CODE_BS);
		Discount discountTwo = Discount.at(10);
		PaymentTransaction paymentTransaction = PaymentTransaction.at(priceTotal, discountTwo, noteReferral);
		Price amountToPay = paymentTransaction.generateAmountToPay();

		assertTrue(amountToPay.compareValue(40.0));
	}

	@Test
	public void verifyApplyDiscountForTwoProduct() {
		Product plate = testObjectBucket.createPlate();
		noteReferral.addProduct(plate, 3);
		Product cup = testObjectBucket.createCup();
		noteReferral.addProduct(cup, 6);

		Map<String, Price> partTotal = noteReferral.calculateTotal();
		Price priceTotal = partTotal.get(TestObjectBucket.CODE_BS);
		Discount discountTwo = Discount.at(30);
		PaymentTransaction paymentTransaction = PaymentTransaction.at(priceTotal, discountTwo, noteReferral);
		Price amountToPay = paymentTransaction.generateAmountToPay();

		assertTrue(amountToPay.compareValue(104.0));
	}

	@Test
	public void todoSingleProductPotSingleReferral() {
		Product pot = testObjectBucket.createPot();
		noteReferral.addProduct(pot, 10);
		Map<String, Price> partTotal = noteReferral.calculateTotal();
		Price priceTotal = partTotal.get(TestObjectBucket.CODE_USA);

		assertTrue(priceTotal.compareValue(17220.0));
	}

	@Test
	public void verifySummarizeTotalBs() {
		Product plate = testObjectBucket.createPlate();
		noteReferral.addProduct(plate, 5);
		// plate -> 50
		Product pot = testObjectBucket.createPot();
		noteReferral.addProduct(pot, 10);
		// pot -> 17220
		Map<String, Price> partTotal = noteReferral.calculateTotal();
		Price priceTotal = partTotal.get(TestObjectBucket.CODE_BS);

		assertTrue(priceTotal.compareValue(50.0));
	}

	@Test
	public void verifySummarizeTotalUSD() {
		Product plate = testObjectBucket.createPlate();
		noteReferral.addProduct(plate, 5);
		// plate -> 50
		Product pot = testObjectBucket.createPot();
		noteReferral.addProduct(pot, 10);
		// pot -> 17220
		Map<String, Price> partTotal = noteReferral.calculateTotal();
		Price priceTotal = partTotal.get(TestObjectBucket.CODE_USA);

		assertTrue(priceTotal.compareValue(17220.0));
	}

	@Test
	public void verifyAmountToPaySingleProduct() {
		Product plate = testObjectBucket.createPlate();
		noteSale.addProduct(plate, 3);
		Map<String, Price> partTotal = noteSale.calculateTotal();
		Price priceTotal = partTotal.get(TestObjectBucket.CODE_BS);

		assertTrue(priceTotal.compareValue(70.0));
	}
}
