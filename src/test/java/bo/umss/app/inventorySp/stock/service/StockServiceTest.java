package bo.umss.app.inventorySp.stock.service;

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
import bo.umss.app.inventorySp.business.measurement.model.Measurement;
import bo.umss.app.inventorySp.business.stock.model.Stock;
import bo.umss.app.inventorySp.business.stock.repository.StockRepository;
import bo.umss.app.inventorySp.business.stock.service.StockService;
import bo.umss.app.inventorySp.exception.EntityNotFoundException;
import bo.umss.app.inventorySp.exception.NegativeFieldException;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { StockServiceTestConfig.class })
public class StockServiceTest {

	@Autowired
	@Qualifier("stockService")
	private StockService stockService;

	@MockBean(name = "stockRepositoryMocked")
	@Qualifier("stockRepository")
	private StockRepository stockRepository;

	private Stock potentialStock;
	private TestObjectBucket testObjectBucket;
	private Integer defaultValue;

	@BeforeEach
	public void setUp() {
		defaultValue = 10;
		testObjectBucket = new TestObjectBucket();
		Measurement measurement = testObjectBucket.createMeasurementPiece();
		potentialStock = testObjectBucket.createStock(defaultValue, measurement);
	}

	@Test
	public void verifyListIsEmpty() {
		List<Stock> stockList = stockService.findAll();

		assertEquals(0, stockList.size());
	}

	@Test
	public void verifyListAddOneItem() {
		List<Stock> responseList = new ArrayList<>();
		responseList.add(potentialStock);
		Mockito.when(stockRepository.findAll()).thenReturn(responseList);
		List<Stock> stockList = stockService.findAll();

		assertEquals(1, stockList.size());
	}

	@Test
	public void verifyWrongCompareValueCreate() {
		Mockito.when(stockRepository.save(Mockito.any())).thenReturn(potentialStock);
		Stock stockResult = stockService.create(potentialStock);

		assertFalse(stockResult.compareOtherValue(defaultValue + 1));
	}

	@Test
	public void verifyCorrectCompareValueCreate() {
		Mockito.when(stockRepository.save(Mockito.any())).thenReturn(potentialStock);
		Stock stockResult = stockService.create(potentialStock);

		assertTrue(stockResult.compareOtherValue(defaultValue));
	}

	@Test
	public void verifyChangeNegativeValueCreate() {
		potentialStock.setValue(-10);
		Mockito.when(stockRepository.save(Mockito.any())).thenReturn(potentialStock);

		assertThrows(NegativeFieldException.class, () -> stockService.create(potentialStock),
				Stock.VALUE_CAN_NOT_BE_LESS_THAN_ZERO);
	}

	@Test
	public void verifyCorrectChangeValueUpdate() {
		potentialStock.setValue(defaultValue + 1);
		Mockito.when(stockRepository.save(Mockito.any())).thenReturn(potentialStock);
		Stock stockResult = stockService.update(potentialStock);

		assertTrue(stockResult.compareOtherValue(potentialStock.getValue()));
	}

	@Test
	public void verifyWrongChangeValueUpdate() {
		potentialStock.setValue(defaultValue + 1);
		Mockito.when(stockRepository.save(Mockito.any())).thenReturn(potentialStock);
		Stock stockResult = stockService.update(potentialStock);

		assertFalse(stockResult.compareOtherValue(defaultValue));
	}

	@Test
	public void verifyChangeNegativeValueUpdate() {
		potentialStock.setValue(-10);
		Mockito.when(stockRepository.save(Mockito.any())).thenReturn(potentialStock);

		assertThrows(NegativeFieldException.class, () -> stockService.update(potentialStock),
				Stock.VALUE_CAN_NOT_BE_LESS_THAN_ZERO);
	}
	
	@Test
	public void verifyDoesntExistFindById() {
		Long key = 5L;

		assertThrows(EntityNotFoundException.class, () -> stockService.read(key));
	}
}
