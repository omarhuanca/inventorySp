package bo.umss.app.inventorySp.line.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import bo.umss.app.inventorySp.TestObjectBucket;
import bo.umss.app.inventorySp.business.line.model.Line;

public class LineTest {

	private Line potentialLine;
	private TestObjectBucket testObjectBucket;

	@BeforeEach
	public void setUp() {
		testObjectBucket = new TestObjectBucket();
		potentialLine = testObjectBucket.createLinePlate();
	}

	@Test
	public void canNotBeEmptyName() {
		assertThrows(RuntimeException.class, () -> Line.at(""), Line.NAME_CAN_NOT_BE_BLANK);
	}

	@Test
	public void verifyCorrectCompareName() {
		assertTrue(potentialLine.compareOtherName(StringUtils.upperCase(TestObjectBucket.PLATE_NAME)));
	}

	@Test
	public void verifyCompareNameIsWrong() {
		potentialLine.setName("x");
		assertFalse(potentialLine.compareOtherName(TestObjectBucket.PLATE_NAME));
	}
}
