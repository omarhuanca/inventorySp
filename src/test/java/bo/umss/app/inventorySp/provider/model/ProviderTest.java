package bo.umss.app.inventorySp.provider.model;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import bo.umss.app.inventorySp.TestObjectBucket;
import bo.umss.app.inventorySp.business.provider.model.Provider;

public class ProviderTest {

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
}
