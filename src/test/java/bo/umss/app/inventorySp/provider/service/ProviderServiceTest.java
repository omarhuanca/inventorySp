package bo.umss.app.inventorySp.provider.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
import bo.umss.app.inventorySp.business.provider.model.Provider;
import bo.umss.app.inventorySp.business.provider.repository.ProviderRepository;
import bo.umss.app.inventorySp.business.provider.service.ProviderService;
import bo.umss.app.inventorySp.exception.EmptyFieldException;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { ProviderServiceTestConfig.class })
public class ProviderServiceTest {

	@Autowired
	@Qualifier("providerService")
	private ProviderService providerService;

	@MockBean(name = "providerRepositoryMocked")
	@Qualifier("providerRepository")
	private ProviderRepository providerRepository;

	private Provider potentialProvider;
	private TestObjectBucket testObjectBucket;

	@BeforeEach
	public void setUp() {
		testObjectBucket = new TestObjectBucket();
		potentialProvider = testObjectBucket.createDefaultProvider();
	}

	@Test
	public void verifyListIsEmpty() {
		List<Provider> providerList = providerService.findAll();

		assertEquals(0, providerList.size());
	}

	@Test
	public void verifyListWasAddOneItem() {
		List<Provider> providerList = providerService.findAll();
		providerList.add(potentialProvider);

		assertEquals(1, providerList.size());
	}

	@Test
	public void verifyDoesntEmptyExistsByName() {
		assertThrows(EmptyFieldException.class, () -> providerService.existsByName(""), Provider.NAME_CAN_NOT_BE_BLANK);
	}

	@Test
	public void verifyCorrectCompareExistsByName() {
		String potentialName = TestObjectBucket.JUAN_PEREZ_NAME;
		Mockito.when(providerRepository.existsByName(Mockito.anyString())).thenReturn(true);

		assertTrue(providerService.existsByName(potentialName));
	}

	@Test
	public void verifyWrongCompareExistsByName() {
		String potentialName = TestObjectBucket.JUAN_PEREZ_NAME;
		Mockito.when(providerRepository.existsByName(Mockito.anyString())).thenReturn(false);

		assertFalse(providerService.existsByName(potentialName + "t"));
	}

	@Test
	public void verifyDoesntBeEmptyFindByName() {
		assertThrows(EmptyFieldException.class, () -> providerService.findByName(""), Provider.NAME_CAN_NOT_BE_BLANK);
	}

	@Test
	public void verifyCorrectCompareFindByName() {
		String potentialName = TestObjectBucket.JUAN_PEREZ_NAME;
		Mockito.when(providerRepository.findByName(Mockito.anyString())).thenReturn(potentialProvider);
		Provider recover = providerService.findByName(potentialName);

		assertTrue(recover.compareAnoherName(potentialName));
	}

	@Test
	public void verifyWrongCompareFindByName() {
		String potentialName = TestObjectBucket.JUAN_PEREZ_NAME;
		Mockito.when(providerRepository.findByName(Mockito.anyString())).thenReturn(potentialProvider);
		Provider recover = providerService.findByName(potentialName);

		assertFalse(recover.compareAnoherName(potentialName + "t"));
	}
}
