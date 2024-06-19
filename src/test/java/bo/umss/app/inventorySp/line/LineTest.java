package bo.umss.app.inventorySp.line;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import bo.umss.app.inventorySp.line.model.Line;

public class LineTest {

	@Test
	public void canNotBeEmptyName() {
		assertThrows(RuntimeException.class, () -> Line.at(""), Line.NAME_CAN_NOT_BE_BLANK);
	}
}
