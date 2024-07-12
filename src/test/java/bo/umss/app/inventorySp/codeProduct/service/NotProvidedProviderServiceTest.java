package bo.umss.app.inventorySp.codeProduct.service;

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
import bo.umss.app.inventorySp.codeProduct.model.NotProvidedProvider;
import bo.umss.app.inventorySp.codeProduct.repository.NotProvidedProviderRepository;
import bo.umss.app.inventorySp.exception.EmptyFieldException;
import bo.umss.app.inventorySp.exception.EntityNotFoundException;
import bo.umss.app.inventorySp.exception.UniqueViolationException;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { NotProvidedProviderServiceTestConfig.class })
public class NotProvidedProviderServiceTest {

	@Autowired
	@Qualifier("notProvidedProviderService")
	private NotProvidedProviderService notProvidedProviderService;

	@MockBean(name = "notProvidedProviderRepositoryMocked")
	@Qualifier("notProvidedProviderRepository")
	private NotProvidedProviderRepository notProvidedProviderRepository;

	private NotProvidedProvider notProvidedProvider;
	private TestObjectBucket testObjectBucket = new TestObjectBucket();

	@BeforeEach
	public void setUp() {
		notProvidedProvider = testObjectBucket.createNotProvidedProviderPlate();
	}

	@Test
	public void verifyGetListResultIsEmpty() {
		List<NotProvidedProvider> notProvidedProviderList = notProvidedProviderService.findAll();
		Mockito.when(notProvidedProviderRepository.findAll()).thenReturn(notProvidedProviderList);
		assertEquals(0, notProvidedProviderList.size());
	}

	@Test
	public void verifyAmountItemAfterWasAdd() {
		List<NotProvidedProvider> notProvidedProviderList = new ArrayList<>();
		NotProvidedProvider notProvidedProvider = (NotProvidedProvider) testObjectBucket
				.createNotProvidedProviderPlate();
		notProvidedProviderList.add(notProvidedProvider);
		Mockito.when(notProvidedProviderRepository.findAll()).thenReturn(notProvidedProviderList);
		assertEquals(1, notProvidedProviderList.size());
	}

	@Test
	public void verifyCodeDoesnotFound() {
		String potentialCode = TestObjectBucket.PLATE_CODE;
		Mockito.when(notProvidedProviderRepository.existsByCode(potentialCode)).thenReturn(false);
		assertFalse(notProvidedProviderService.existsByCode(potentialCode));
	}

	@Test
	public void verifycodeWasFound() {
		String potentialCode = TestObjectBucket.PLATE_CODE;
		Mockito.when(notProvidedProviderRepository.existsByCode(potentialCode)).thenReturn(true);
		assertTrue(notProvidedProviderService.existsByCode(potentialCode));
	}

	@Test
	public void executeFoundCodeAfterThrowException() {
		Mockito.when(notProvidedProviderRepository.existsByCode(TestObjectBucket.PLATE_CODE)).thenReturn(true);
		assertThrows(UniqueViolationException.class, () -> notProvidedProviderService.create(notProvidedProvider));
	}

	@Test
	public void verifyExecuteThrowException() {
		assertThrows(EmptyFieldException.class, () -> notProvidedProviderService.findByCode(""));
	}

	@Test
	public void verifyCompareCodeWrongNotProvider() {
		String potentialCode = "PLA-2";
		NotProvidedProvider anotherNotProvidedProvider = testObjectBucket.createNotProvidedProviderCup();
		Mockito.when(notProvidedProviderRepository.findByCode(potentialCode)).thenReturn(notProvidedProvider);
		NotProvidedProvider recover = notProvidedProviderService.findByCode(potentialCode);

		assertFalse(recover.compareAnotherCode(anotherNotProvidedProvider));
	}

	@Test
	public void verifyCompareCodeSuccessNotProvider() {
		String potentialCode = "PLA-2";
		Mockito.when(notProvidedProviderRepository.findByCode(potentialCode)).thenReturn(notProvidedProvider);
		NotProvidedProvider recover = notProvidedProviderService.findByCode(potentialCode);

		assertTrue(recover.compareAnotherCode(notProvidedProvider));
	}

	@Test
	public void verifyWronCompareCode() {
		NotProvidedProvider anotherNotProvidedProvider = testObjectBucket.createNotProvidedProviderCup();
		Mockito.when(notProvidedProviderRepository.save(notProvidedProvider)).thenReturn(anotherNotProvidedProvider);
		NotProvidedProvider recovered = notProvidedProviderService.create(notProvidedProvider);
		assertFalse(recovered.compareAnotherCode(notProvidedProvider));
	}

	@Test
	public void verifyCorrectCompareCode() {
		Mockito.when(notProvidedProviderRepository.save(notProvidedProvider)).thenReturn(notProvidedProvider);
		NotProvidedProvider recovered = notProvidedProviderService.create(notProvidedProvider);
		assertTrue(recovered.compareAnotherCode(notProvidedProvider));
	}

	@Test
	public void verifyChangeAfterUpdateChange() {
		NotProvidedProvider anotherNotProvidedProvider = testObjectBucket.createNotProvidedProviderCup();
		Mockito.when(notProvidedProviderRepository.save(notProvidedProvider)).thenReturn(anotherNotProvidedProvider);
		NotProvidedProvider recovered = notProvidedProviderService.update(notProvidedProvider);
		assertTrue(recovered.compareAnotherCode(anotherNotProvidedProvider));
	}

	@Test
	public void verifyThrowException() {
		Long key = 5L;
		assertThrows(EntityNotFoundException.class, () -> notProvidedProviderService.read(key));
	}

	@Test
	public void verifyCompareCodeWrong() {
		Long key = 10L;
		NotProvidedProvider anotherNotProvidedProvider = testObjectBucket.createNotProvidedProviderCup();
		Optional<NotProvidedProvider> notProvidedProviderOptional = Optional.of(anotherNotProvidedProvider);

		Mockito.when(notProvidedProviderRepository.findById(key)).thenReturn(notProvidedProviderOptional);
		NotProvidedProvider recoveryNotProvidedProvider = notProvidedProviderService.read(key);

		assertFalse(recoveryNotProvidedProvider.compareAnotherCode(notProvidedProvider));
	}

	@Test
	public void verifyCompareCodeSuccess() {
		Long key = 10L;
		NotProvidedProvider anotherNotProvidedProvider = testObjectBucket.createNotProvidedProviderCup();
		Optional<NotProvidedProvider> notProvidedProviderOptional = Optional.of(anotherNotProvidedProvider);

		Mockito.when(notProvidedProviderRepository.findById(key)).thenReturn(notProvidedProviderOptional);
		NotProvidedProvider recoveryNotProvidedProvider = notProvidedProviderService.read(key);

		assertTrue(recoveryNotProvidedProvider.compareAnotherCode(anotherNotProvidedProvider));
	}
}
