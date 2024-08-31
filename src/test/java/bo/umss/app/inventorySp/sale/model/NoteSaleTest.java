package bo.umss.app.inventorySp.sale.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import bo.umss.app.inventorySp.NoteTransaction;
import bo.umss.app.inventorySp.TestObjectBucket;
import bo.umss.app.inventorySp.business.product.model.Product;
import bo.umss.app.inventorySp.business.sale.model.NoteSale;

public class NoteSaleTest {

	private LocalDate date;
	private String invoiceNumber;
	private final TestObjectBucket testObjectBucket = new TestObjectBucket();

	@BeforeEach
	public void setUp() {
		date = testObjectBucket.createDate();
		invoiceNumber = TestObjectBucket.INVOICE_NUMBER;
	}

	@Test
	public void canNotBeNullDate() {
		assertThrows(RuntimeException.class, () -> NoteSale.at(null, TestObjectBucket.JUAN_PEREZ_NAME,
				TestObjectBucket.JUAN_PEREZ_NIT, invoiceNumber), NoteTransaction.DATE_CAN_NOT_BE_NULL);
	}

	@Test
	public void canNotBeEmptyName() {
		assertThrows(RuntimeException.class,
				() -> NoteSale.at(date, "", TestObjectBucket.JUAN_PEREZ_NIT, invoiceNumber),
				NoteSale.NAME_CAN_NOT_BE_EMPTY);
	}

	@Test
	public void canNotBeEmptyNit() {
		assertThrows(RuntimeException.class,
				() -> NoteSale.at(date, TestObjectBucket.JUAN_PEREZ_NAME, "", invoiceNumber),
				NoteSale.NIT_CAN_NOT_BE_EMPTY);
	}

	@Test
	public void canNotBeEmptyInvoiceNumber() {
		assertThrows(RuntimeException.class,
				() -> NoteSale.at(date, TestObjectBucket.JUAN_PEREZ_NAME, TestObjectBucket.JUAN_PEREZ_NIT, ""),
				NoteSale.INVOICE_NUMBER_CAN_NOT_BE_EMPTY);
	}

	@Test
	public void verifySizeListProductIsEmpty() {
		NoteSale noteSale = NoteSale.at(date, TestObjectBucket.JUAN_PEREZ_NAME, TestObjectBucket.JUAN_PEREZ_NIT,
				invoiceNumber);

		assertTrue(noteSale.compareSizeGreaterZero(0));
	}

	@Test
	public void verifyNotAddProductAmountGreaterThanStockValue() {
		NoteSale noteSale = NoteSale.at(date, TestObjectBucket.JUAN_PEREZ_NAME, TestObjectBucket.JUAN_PEREZ_NIT,
				invoiceNumber);
		Product plate = testObjectBucket.createPlate();

		assertFalse(noteSale.addProduct(plate, 12));
	}

	@Test
	public void verifyAddProductAmountLessThanStockValue() {
		NoteSale noteSale = NoteSale.at(date, TestObjectBucket.JUAN_PEREZ_NAME, TestObjectBucket.JUAN_PEREZ_NIT,
				invoiceNumber);
		Product plate = testObjectBucket.createPlate();

		assertTrue(noteSale.addProduct(plate, 8));
	}

	@Test
	public void verifyProductWasNotAddStockLessThan() {
		NoteSale noteSale = NoteSale.at(date, TestObjectBucket.JUAN_PEREZ_NAME, TestObjectBucket.JUAN_PEREZ_NIT,
				invoiceNumber);
		Product cup = testObjectBucket.createCup();
		noteSale.addProduct(cup, 11);

		assertTrue(noteSale.compareIsEqualSize(0));
	}

	@Test
	public void verifyManyProductWasAdd() {
		NoteSale noteSale = NoteSale.at(date, TestObjectBucket.JUAN_PEREZ_NAME, TestObjectBucket.JUAN_PEREZ_NIT,
				invoiceNumber);
		Product cup = testObjectBucket.createCup();
		Product plate = testObjectBucket.createPlate();
		Product pot = testObjectBucket.createPot();
		noteSale.addProduct(cup, 8);
		noteSale.addProduct(plate, 5);
		noteSale.addProduct(pot, 40);

		assertTrue(noteSale.compareIsEqualSize(3));
	}
}
