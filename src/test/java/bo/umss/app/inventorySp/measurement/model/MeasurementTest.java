package bo.umss.app.inventorySp.measurement.model;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import bo.umss.app.inventorySp.business.measurement.model.Measurement;

public class MeasurementTest {

	@Test
	public void canNotBeEmptyName() {
		assertThrows(RuntimeException.class, () -> Measurement.at(""),
				Measurement.CODE_CAN_NOT_BE_BLANK);
	}
}
