package bo.umss.app.inventorySp.coin.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import bo.umss.app.inventorySp.TestObjectBucket;
import bo.umss.app.inventorySp.business.coin.model.Coin;
import bo.umss.app.inventorySp.business.coin.repository.CoinRepository;
import bo.umss.app.inventorySp.business.coin.service.CoinService;
import bo.umss.app.inventorySp.exception.EmptyFieldException;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { CoinServiceTestConfig.class })
public class CoinServiceTest {

	@Autowired
	@Qualifier("coinService")
	private CoinService coinService;

	@MockBean(name = "coinRepositoryMocked")
	@Qualifier("coinRepository")
	private CoinRepository coinRepository;

	private Coin potentialCoin;
	private TestObjectBucket testObjectBucket = new TestObjectBucket();

	@BeforeEach
	public void setUp() {
		potentialCoin = testObjectBucket.createCoin();
	}

	@Test
	public void veridyNotExistsCode() {
		String potentialCode = "code02";

		assertThrows(RuntimeException.class, () -> coinService.findByCode(potentialCode));
	}

	@Test
	public void verifyWrongCompareCode() {
		String potentialCode = "code";
		Mockito.when(coinRepository.findByCode(TestObjectBucket.CODE_BS)).thenReturn(potentialCoin);
		Coin coin = coinService.findByCode(TestObjectBucket.CODE_BS);

		assertFalse(coin.compareOtherCode(potentialCode));
	}

	@Test
	public void verifyCorrectCompareCode() {
		Mockito.when(coinRepository.findByCode(TestObjectBucket.CODE_BS)).thenReturn(potentialCoin);
		Coin coin = coinService.findByCode(TestObjectBucket.CODE_BS);

		assertTrue(coin.compareOtherCode(TestObjectBucket.CODE_BS));
	}

	@Test
	public void verifyNotLetEmptyCode() {
		assertThrows(EmptyFieldException.class, () -> coinService.existsByCode(""));
	}

	@Test
	public void verifyWrongExistsCode() {
		String potentialCode = "USD";

		assertFalse(coinService.existsByCode(potentialCode));
	}

	@Test
	public void verifyCorrectExistsCode() {
		String potentialCode = "USD";
		Mockito.when(coinRepository.existsByCode(potentialCode)).thenReturn(Boolean.TRUE);

		assertTrue(coinService.existsByCode(potentialCode));
	}

	@Test
	public void verifyListIsEmpty() {
		List<Coin> coinList = coinService.findAll();

		assertEquals(0, coinList.size());
	}

	@Test
	public void verifyListWasAddOneItem() {
		List<Coin> responseList = new ArrayList<>();
		responseList.add(potentialCoin);
		Mockito.when(coinRepository.findAll()).thenReturn(responseList);
		List<Coin> coinList = coinService.findAll();

		assertEquals(1, coinList.size());
	}

	@Test
	public void test10() {
		Mockito.when(coinRepository.existsByCode(TestObjectBucket.CODE_BS)).thenReturn(Boolean.TRUE);

		assertThrows(RuntimeException.class, () -> coinService.create(potentialCoin));
	}

	@Test
	public void test11() {
		Mockito.when(coinRepository.save(potentialCoin)).thenReturn(potentialCoin);
		Coin coinResult = coinService.create(potentialCoin);

		assertFalse(coinResult.compareOtherCode(TestObjectBucket.CODE_BS + "a"));
	}

	@Test
	public void test12() {
		Mockito.when(coinRepository.save(potentialCoin)).thenReturn(potentialCoin);
		Coin coinResult = coinService.create(potentialCoin);

		assertTrue(coinResult.compareOtherCode(TestObjectBucket.CODE_BS));
	}
}
