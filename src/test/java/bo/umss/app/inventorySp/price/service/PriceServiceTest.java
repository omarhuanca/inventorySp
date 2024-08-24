package bo.umss.app.inventorySp.price.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import bo.umss.app.inventorySp.exception.EmptyFieldException;
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
	private String potentialCode;

	@BeforeEach
	public void setUp() {
		defaultValue = 15.0;
		potentialCode = "PR-1";
		testObjectBucket = new TestObjectBucket();
		Coin coin = testObjectBucket.createCoin(TestObjectBucket.CODE_BS);
		potentialPrice = testObjectBucket.createPrice("PR-1", defaultValue, coin);
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
	public void verifyIsEmptyCode() {
		assertThrows(EmptyFieldException.class, () -> priceService.existsByCode(""), Price.CODE_CAN_NOT_BE_BLANK);
	}

	@Test
	public void verifyDoesntFoundExistsByCode() {
		Mockito.when(priceRepository.existsByCode(Mockito.anyString())).thenReturn(false);

		assertFalse(priceService.existsByCode(potentialCode));
	}

	@Test
	public void verifyAlreadyExistsByCode() {
		Mockito.when(priceRepository.existsByCode(Mockito.anyString())).thenReturn(true);

		assertTrue(priceService.existsByCode(potentialCode));
	}

	@Test
	public void verifyIsEmptyFindByCode() {
		assertThrows(EmptyFieldException.class, () -> priceService.findByCode(""), Price.CODE_CAN_NOT_BE_BLANK);
	}

	@Test
	public void verifyCorrectCompareFindByCode() {
		Mockito.when(priceRepository.findByCode(Mockito.anyString())).thenReturn(potentialPrice);
		Price recover = priceService.findByCode(potentialCode);

		assertTrue(recover.compareAnotherCode(potentialCode));
	}

	@Test
	public void verifyWrongCompareFindByCode() {
		Mockito.when(priceRepository.findByCode(Mockito.anyString())).thenReturn(potentialPrice);
		Price recover = priceService.findByCode(potentialCode);

		assertFalse(recover.compareAnotherCode(potentialCode + "t"));
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
				Price.CODE_CAN_NOT_BE_BLANK);
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
				Price.CODE_CAN_NOT_BE_BLANK);
	}

	@Test
	public void verifyDoesntFoundByRead() {
		Long key = 6L;
		assertThrows(EntityNotFoundException.class, () -> priceService.read(key));
	}

	@Test
	public void verifySuccessFoundByRead() {
		Long key = 2L;
		Optional<Price> optionalPrice = Optional.of(potentialPrice);
		Mockito.when(priceRepository.findById(Mockito.any())).thenReturn(optionalPrice);
		Price recover = priceService.read(key);

		assertTrue(recover.compareAnotherCode(potentialCode));
	}

	@Test
	public void verifyFailureFoundByRead() {
		Long key = 2L;
		Optional<Price> optionalPrice = Optional.of(potentialPrice);
		Mockito.when(priceRepository.findById(Mockito.any())).thenReturn(optionalPrice);
		Price recover = priceService.read(key);

		assertFalse(recover.compareAnotherCode(potentialCode + "t"));
	}
}
