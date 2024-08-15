package bo.umss.app.inventorySp.coin.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import bo.umss.app.inventorySp.business.coin.model.Coin;

public class CoinTest {

	private Coin coin;

	@BeforeEach
	public void setUp() {
		String potentialCode = "EUR";
		coin = Coin.at(potentialCode);
	}

	@Test
	public void verifyCodeCannotBeEmpty() {
		assertThrows(RuntimeException.class, () -> Coin.at(""), Coin.CODE_CAN_NOT_BE_BLANK);
	}

	@Test
	public void verifyWrongName() {
		assertFalse(coin.compareOtherCode("IN"));
	}

	@Test
	public void verifyCorrectName() {
		assertTrue(coin.compareOtherCode("euR"));
	}
}
