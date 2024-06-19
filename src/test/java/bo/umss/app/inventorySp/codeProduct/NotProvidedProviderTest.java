package bo.umss.app.inventorySp.codeProduct;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import bo.umss.app.inventorySp.TestObjectBucket;
import bo.umss.app.inventorySp.codeProduct.model.NotProvidedProvider;
import bo.umss.app.inventorySp.line.model.Line;

public class NotProvidedProviderTest {

	private Line line;

	@BeforeEach
	public void setUp() {
		line = Line.at(TestObjectBucket.PLATE_NAME);
	}

	@Test
	public void canNotCreateBlankCode() {
		assertThrows(RuntimeException.class, () -> NotProvidedProvider.at("", TestObjectBucket.BOWL8_DESCRIPTION, line),
				CodeProduct.CODE_CAN_NOT_BE_BLANK);
	}

	@Test
	public void canNotCreateBlankDescription() {
		assertThrows(RuntimeException.class, () -> NotProvidedProvider.at(TestObjectBucket.BOWL8_CODE, "", line),
				CodeProduct.DESCRIPTION_CAN_NOT_BE_BLANK);
	}

	@Test
	public void canNotCreateNullLine() {
		assertThrows(RuntimeException.class,
				() -> NotProvidedProvider.at(TestObjectBucket.BOWL8_CODE, TestObjectBucket.BOWL8_DESCRIPTION, null),
				CodeProduct.DESCRIPTION_CAN_NOT_BE_BLANK);
	}
}
