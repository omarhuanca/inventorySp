package bo.umss.app.inventorySp.line.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import bo.umss.app.inventorySp.line.model.Line;

public class LineTest {

	private Line line;

	@BeforeEach
	public void setUp() {
		String name = "juego de ollas";
		line = Line.at(name);
	}

	@Test
	public void canNotBeEmptyName() {
		assertThrows(RuntimeException.class, () -> Line.at(""), Line.NAME_CAN_NOT_BE_BLANK);
	}

	@Test
	public void verifySameName() {
		assertFalse(line.compareOtherName("juego de cubierto"));
	}

	@Test
	public void verifyCorrectCompareName() {
		assertTrue(line.compareOtherName("JUEGO de OLLas"));
	}
}
