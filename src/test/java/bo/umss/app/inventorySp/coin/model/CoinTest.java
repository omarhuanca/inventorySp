package bo.umss.app.inventorySp.coin.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import bo.umss.app.inventorySp.TestObjectBucket;
import bo.umss.app.inventorySp.business.coin.model.Coin;

public class CoinTest {

	private Coin potentialCoin;
	private TestObjectBucket testObjectBucket;

	@BeforeEach
	public void setUp() {
		testObjectBucket = new TestObjectBucket();
		potentialCoin = testObjectBucket.createCoin(TestObjectBucket.CODE_USD);
	}

	@Test
	public void verifyCodeCannotBeEmpty() {
		assertThrows(RuntimeException.class, () -> Coin.at(""), Coin.CODE_CAN_NOT_BE_BLANK);
	}

	@Test
	public void verifyWrongName() {
		assertFalse(potentialCoin.compareOtherCode("IN"));
	}

	@Test
	public void verifyCorrectName() {
		assertTrue(potentialCoin.compareOtherCode("usd"));
	}

	@Test
	public void verifyCompareCodeIsCorrect() {
		assertTrue(potentialCoin.compareOtherCode(TestObjectBucket.CODE_USD));
	}

	@Test
	public void verifyCompareCodeIsWrong() {
		potentialCoin.setCode("coin");
		assertFalse(potentialCoin.compareOtherCode(TestObjectBucket.CODE_USD));
	}
}
