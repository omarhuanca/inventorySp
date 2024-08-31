package bo.umss.app.inventorySp.provider.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import bo.umss.app.inventorySp.TestObjectBucket;
import bo.umss.app.inventorySp.business.provider.model.Provider;

public class ProviderTest {

	private Provider potentialProvider;
	private TestObjectBucket testObjectBucket;

	@BeforeEach
	public void setUp() {
		testObjectBucket = new TestObjectBucket();
		potentialProvider = testObjectBucket.createDefaultProvider();
	}

	@Test
	public void canNotCreateEmptyName() {
		assertThrows(RuntimeException.class, () -> Provider.at("", TestObjectBucket.JUAN_PEREZ_CELLPHONE),
				Provider.NAME_CAN_NOT_BE_BLANK);
	}

	@Test
	public void canNotCreateEmptyPhoneNumber() {
		assertThrows(RuntimeException.class, () -> Provider.at(TestObjectBucket.JUAN_PEREZ_NAME, ""),
				Provider.NAME_CAN_NOT_BE_BLANK);
	}

	@Test
	public void verifySuccessEqual() {
		assertTrue(potentialProvider.compareAnoherName(TestObjectBucket.JUAN_PEREZ_NAME));
	}

	@Test
	public void verifyFailureEqual() {
		assertFalse(potentialProvider.compareAnoherName(TestObjectBucket.JUAN_PEREZ_NAME + "t"));
	}
}
