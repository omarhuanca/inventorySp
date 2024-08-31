package bo.umss.app.inventorySp.changePrice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import bo.umss.app.inventorySp.TestObjectBucket;
import bo.umss.app.inventorySp.business.changePrice.model.ChangePrice;
import bo.umss.app.inventorySp.business.changePrice.repository.ChangePriceRepository;
import bo.umss.app.inventorySp.business.changePrice.service.ChangePriceService;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { ChangePriceServiceTestConfig.class })
public class ChangePriceServiceTest {

	@Autowired
	@Qualifier("changePriceService")
	private ChangePriceService changePriceService;

	@MockBean(name = "changePriceRepositoryMocked")
	@Qualifier("changePriceRepository")
	private ChangePriceRepository changePriceRepository;

	private TestObjectBucket testObjectBucket;
	private ChangePrice potentialChangePrice;

	@BeforeEach
	public void setUp() {
		testObjectBucket = new TestObjectBucket();
		potentialChangePrice = testObjectBucket.createChangePrice();
	}

	@Test
	public void verifyListFetchEmpty() {
		List<ChangePrice> changePriceList = changePriceRepository.findAll();

		assertEquals(0, changePriceList.size());
	}

	@Test
	public void verifyListWasAddItem() {
		List<ChangePrice> changePriceList = new ArrayList<>();
		changePriceList.add(potentialChangePrice);

		assertEquals(1, changePriceList.size());
	}
}
