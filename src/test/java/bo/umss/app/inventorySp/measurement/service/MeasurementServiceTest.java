package bo.umss.app.inventorySp.measurement.service;

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
import bo.umss.app.inventorySp.business.measurement.repository.MeasurementRepository;
import bo.umss.app.inventorySp.business.measurement.service.MeasurementService;
import bo.umss.app.inventorySp.exception.EmptyFieldException;
import bo.umss.app.inventorySp.exception.EntityNotFoundException;
import bo.umss.app.inventorySp.exception.UniqueViolationException;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { MeasurementServiceTestConfig.class })
public class MeasurementServiceTest {

	@Autowired
	@Qualifier("measurementService")
	private MeasurementService measurementService;

	@MockBean(name = "measurementRepositoryMocked")
	@Qualifier("measurementRepository")
	private MeasurementRepository measurementRepository;

	private Measurement potentialMeasurement;
	private TestObjectBucket testObjectBucket = new TestObjectBucket();
	private String potentialCode;

	@BeforeEach
	public void setUp() {
		potentialMeasurement = testObjectBucket.createMeasurementPiece();
		potentialCode = TestObjectBucket.CODE_PZA;
	}

	@Test
	public void verifyNotFoundRecord() {
		assertThrows(EntityNotFoundException.class, () -> measurementService.findByCode(potentialCode));
	}

	@Test
	public void verifyFindByCodeIsEmpty() {
		assertThrows(EmptyFieldException.class, () -> measurementService.findByCode(""));
	}

	@Test
	public void verifyExistsByCodeIsEmpty() {
		assertThrows(EmptyFieldException.class, () -> measurementService.existsByCode(""));
	}

	@Test
	public void verifyWrongCompareCode() {
		Mockito.when(measurementRepository.findByCode(TestObjectBucket.CODE_PZA)).thenReturn(potentialMeasurement);
		Measurement measurementRecover = measurementService.findByCode(TestObjectBucket.CODE_PZA);

		assertFalse(measurementRecover.compareOtherCode(potentialCode + "c"));
	}

	@Test
	public void verifySuccessCompareCode() {
		Mockito.when(measurementRepository.findByCode(TestObjectBucket.CODE_PZA)).thenReturn(potentialMeasurement);
		Measurement measurementRecover = measurementService.findByCode(TestObjectBucket.CODE_PZA);

		assertTrue(measurementRecover.compareOtherCode(TestObjectBucket.CODE_PZA));
	}

	@Test
	public void verifyEmptyList() {
		List<Measurement> measurementList = measurementService.findAll();

		assertEquals(0, measurementList.size());
	}

	@Test
	public void verifyWasAddOneItemList() {
		List<Measurement> responseList = new ArrayList<>();
		responseList.add(potentialMeasurement);
		Mockito.when(measurementRepository.findAll()).thenReturn(responseList);
		List<Measurement> measurementList = measurementService.findAll();

		assertEquals(1, measurementList.size());
	}

	@Test
	public void verifyAlreadyCode() {
		Mockito.when(measurementRepository.existsByCode(TestObjectBucket.CODE_PZA)).thenReturn(Boolean.TRUE);

		assertThrows(UniqueViolationException.class, () -> measurementService.create(potentialMeasurement));
	}

	@Test
	public void verifyMeasurementFetchCorrectCompare() {
		Mockito.when(measurementRepository.save(Mockito.any())).thenReturn(potentialMeasurement);
		Measurement measurementeResult = measurementService.create(potentialMeasurement);

		assertTrue(measurementeResult.compareOtherCode(potentialCode));
	}

	@Test
	public void verifyMeasurementFetchWrongCompare() {
		Mockito.when(measurementRepository.save(Mockito.any())).thenReturn(potentialMeasurement);
		Measurement measurementeResult = measurementService.create(potentialMeasurement);

		assertFalse(measurementeResult.compareOtherCode(potentialCode + "f"));
	}
}
