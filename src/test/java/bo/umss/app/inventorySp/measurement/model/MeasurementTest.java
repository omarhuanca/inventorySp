package bo.umss.app.inventorySp.measurement.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import bo.umss.app.inventorySp.TestObjectBucket;
import bo.umss.app.inventorySp.business.measurement.model.Measurement;

public class MeasurementTest {

	private TestObjectBucket testObjectBucket;
	private Measurement potentialMeasurement;

	@BeforeEach
	public void setUp() {
		testObjectBucket = new TestObjectBucket();
		potentialMeasurement = testObjectBucket.createMeasurementPiece();
	}

	@Test
	public void canNotBeEmptyName() {
		assertThrows(RuntimeException.class, () -> Measurement.at(""), Measurement.CODE_CAN_NOT_BE_BLANK);
	}

	@Test
	public void verifyCompareCodeIsCorrect() {
		assertTrue(potentialMeasurement.compareOtherCode(TestObjectBucket.CODE_PZA));
	}

	@Test
	public void verifyCompareCodeIsWrong() {
		potentialMeasurement.setCode("t");
		assertFalse(potentialMeasurement.compareOtherCode(TestObjectBucket.CODE_PZA));
	}
}
