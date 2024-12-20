package bo.umss.app.inventorySp.price.service;

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
import bo.umss.app.inventorySp.business.price.model.Price;
import bo.umss.app.inventorySp.business.price.repository.PriceRepository;
import bo.umss.app.inventorySp.business.price.service.PriceService;
import bo.umss.app.inventorySp.exception.EntityNotFoundException;
import bo.umss.app.inventorySp.exception.NegativeFieldException;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { PriceServiceTestConfig.class })
public class PriceServiceTest {

	@Autowired
	@Qualifier("priceService")
	private PriceService priceService;

	@MockBean(name = "priceRepositoryMocked")
	@Qualifier("priceRepository")
	private PriceRepository priceRepository;

	private Price potentialPrice;
	private TestObjectBucket testObjectBucket;
	private Double defaultValue;

	@BeforeEach
	public void setUp() {
		defaultValue = 15.0;
		testObjectBucket = new TestObjectBucket();
		Coin coin = testObjectBucket.createCoin(TestObjectBucket.CODE_BS);
		potentialPrice = testObjectBucket.createPrice(defaultValue, coin);
	}

	@Test
	public void verifyListIsEmpty() {
		List<Price> priceList = priceService.findAll();

		assertEquals(0, priceList.size());
	}

	@Test
	public void verifyListHasOneItem() {
		List<Price> priceList = new ArrayList<>();
		priceList.add(potentialPrice);
		Mockito.when(priceRepository.findAll()).thenReturn(priceList);

		assertEquals(1, priceList.size());
	}

	@Test
	public void verifyWrongCompareValueCreate() {
		Mockito.when(priceRepository.save(potentialPrice)).thenReturn(potentialPrice);
		Price recovered = priceService.create(potentialPrice);

		assertFalse(recovered.compareOtherValue(defaultValue + 1));
	}

	@Test
	public void verifyCorrectCompareValueCreate() {
		Mockito.when(priceRepository.save(potentialPrice)).thenReturn(potentialPrice);
		Price recovered = priceService.create(potentialPrice);

		assertTrue(recovered.compareOtherValue(defaultValue));
	}

	@Test
	public void verifyNegativeValueCreate() {
		potentialPrice.setValue(-5.0);

		assertThrows(NegativeFieldException.class, () -> priceService.create(potentialPrice),
				Price.VALUE_CAN_NOT_BE_LESS_ZERO);
	}

	@Test
	public void verifyWasAfterValueUpdate() {
		potentialPrice.setValue(defaultValue + 1);
		Mockito.when(priceRepository.save(potentialPrice)).thenReturn(potentialPrice);
		Price priceResult = priceService.update(potentialPrice);

		assertTrue(priceResult.compareOtherValue(potentialPrice.getValue()));
	}

	@Test
	public void verifyWasntValueUpdate() {
		potentialPrice.setValue(defaultValue + 1);
		Mockito.when(priceRepository.save(potentialPrice)).thenReturn(potentialPrice);
		Price priceResult = priceService.update(potentialPrice);

		assertFalse(priceResult.compareOtherValue(defaultValue));
	}

	@Test
	public void verifyNegativeValueUpdate() {
		potentialPrice.setValue(-5.0);

		assertThrows(NegativeFieldException.class, () -> priceService.update(potentialPrice),
				Price.VALUE_CAN_NOT_BE_LESS_ZERO);
	}

	@Test
	public void verifyDoesntFoundByRead() {
		Long key = 6L;
		assertThrows(EntityNotFoundException.class, () -> priceService.read(key));
	}
}
