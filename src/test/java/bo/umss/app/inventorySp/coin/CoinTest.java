package bo.umss.app.inventorySp.coin;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import bo.umss.app.inventorySp.coin.model.Coin;

public class CoinTest {

	@Test
	public void verifyCodeCannotBeEmpty() {
		assertThrows(RuntimeException.class, () -> Coin.at(""), Coin.CODE_CAN_NOT_BE_EMPTY);
	}
}
