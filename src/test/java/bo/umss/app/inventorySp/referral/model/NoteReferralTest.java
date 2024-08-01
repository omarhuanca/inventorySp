package bo.umss.app.inventorySp.referral.model;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import bo.umss.app.inventorySp.NoteTransaction;
import bo.umss.app.inventorySp.TestObjectBucket;
import bo.umss.app.inventorySp.business.product.model.Product;
import bo.umss.app.inventorySp.business.referral.model.NoteReferral;

public class NoteReferralTest {

	private LocalDate date;
	private final TestObjectBucket testObjectBucket = new TestObjectBucket();

	@BeforeEach
	public void setUp() {
		date = testObjectBucket.createDate();
	}

	@Test
	public void canNotBeNullDate() {
		assertThrows(RuntimeException.class, () -> NoteReferral.at(null), NoteTransaction.DATE_CAN_NOT_BE_NULL);
	}

	@Test
	public void verifySizeEmptyAfterCreateNoteReferral() {
		NoteReferral noteReferral = NoteReferral.at(date);

		assertTrue(noteReferral.compareIsEqualSize(0));
	}

	@Test
	public void verifySizeAmountGreaterThanStockValue() {
		NoteReferral noteReferral = NoteReferral.at(date);
		Product plate = testObjectBucket.createPlate();
		noteReferral.addProduct(plate, 20);

		assertTrue(noteReferral.compareIsEqualSize(0));
	}

	@Test
	public void verifySizeAfterAddOneReferral() {
		NoteReferral noteReferral = NoteReferral.at(date);
		Product cup = testObjectBucket.createCup();
		noteReferral.addProduct(cup, 5);

		assertTrue(noteReferral.compareIsEqualSize(1));
	}
}
