package bo.umss.app.inventorySp.product.service;

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
import bo.umss.app.inventorySp.business.product.model.Product;
import bo.umss.app.inventorySp.business.product.repository.ProductRepository;
import bo.umss.app.inventorySp.business.product.service.ProductService;
import bo.umss.app.inventorySp.exception.EmptyFieldException;
import bo.umss.app.inventorySp.exception.EntityNotFoundException;
import bo.umss.app.inventorySp.exception.UniqueViolationException;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { ProductServiceTestConfig.class })
public class ProductServiceTest {

	@Autowired
	@Qualifier("productService")
	private ProductService productService;

	@MockBean(name = "productRepositoryMocked")
	@Qualifier("productRepository")
	private ProductRepository productRepository;

	private Product potentialProduct;
	private TestObjectBucket testObjectBucket;
	private String potentialCode;

	@BeforeEach
	public void setUp() {
		testObjectBucket = new TestObjectBucket();
		potentialProduct = testObjectBucket.createPlate();
		potentialCode = "PLA-1";
	}

	@Test
	public void verifyListIsEmpty() {
		List<Product> productList = productService.findAll();

		assertEquals(0, productList.size());
	}

	@Test
	public void verifyListAddItem() {
		List<Product> responseList = new ArrayList<>();
		responseList.add(potentialProduct);
		Mockito.when(productRepository.findAll()).thenReturn(responseList);
		List<Product> productList = productService.findAll();

		assertEquals(1, productList.size());
	}

	@Test
	public void verifyIsEmptyCode() {
		assertThrows(EmptyFieldException.class, () -> productService.existsByCode(""), Product.CODE_CAN_NOT_BE_BLANK);
	}

	@Test
	public void verifyWrongMatchCode() {
		Mockito.when(productRepository.existsByCode(Mockito.anyString())).thenReturn(false);

		assertFalse(productService.existsByCode(potentialCode));
	}

	@Test
	public void verifyCorrectMatchCode() {
		Mockito.when(productRepository.existsByCode(Mockito.anyString())).thenReturn(true);

		assertTrue(productService.existsByCode(potentialCode));
	}

	@Test
	public void verifyCreateAlreadyCode() {
		Mockito.when(productRepository.existsByCode(Mockito.anyString())).thenReturn(true);

		assertThrows(UniqueViolationException.class, () -> productService.create(potentialProduct));
	}

	@Test
	public void verifyCreateCodeEmptyWrong() {
		potentialProduct.setCode("");

		assertThrows(EmptyFieldException.class, () -> productService.create(potentialProduct),
				Product.CODE_CAN_NOT_BE_BLANK);
	}

	@Test
	public void verifyCreateDescriptionEmptyWrong() {
		potentialProduct.setDescription("");

		assertThrows(EmptyFieldException.class, () -> productService.create(potentialProduct),
				Product.DESCRIPTION_CAN_NOT_BE_BLANK);
	}

	@Test
	public void verifyUpdateCanNotBeEmptyCode() {
		potentialProduct.setCode("");

		assertThrows(EmptyFieldException.class, () -> productService.update(potentialProduct),
				Product.CODE_CAN_NOT_BE_BLANK);
	}

	@Test
	public void verifyUpdateCanNotBeEmptyDescription() {
		potentialProduct.setDescription("");

		assertThrows(EmptyFieldException.class, () -> productService.update(potentialProduct),
				Product.DESCRIPTION_CAN_NOT_BE_BLANK);
	}

	@Test
	public void verifyFindByCodeIsEmpty() {
		assertThrows(EmptyFieldException.class, () -> productService.findByCode(""));
	}

	@Test
	public void verifyCompareCodeSuccess() {
		Mockito.when(productRepository.findByCode(Mockito.anyString())).thenReturn(potentialProduct);
		Product recover = productService.findByCode(potentialCode);

		assertTrue(recover.compareOtherCode(potentialCode));
	}

	@Test
	public void verifyCompareCodeWrong() {
		Mockito.when(productRepository.findByCode(Mockito.anyString())).thenReturn(potentialProduct);
		Product recover = productService.findByCode(potentialCode);

		assertFalse(recover.compareOtherCode(potentialCode + "t"));
	}

	@Test
	public void verifyNotReadId() {
		Long key = 2L;
		assertThrows(EntityNotFoundException.class, () -> productService.read(key));
	}

	@Test
	public void verifyCompareCodeReadSuccess() {
		Long key = 2L;
		Optional<Product> optionalProduct = Optional.of(potentialProduct);
		Mockito.when(productRepository.findById(Mockito.any())).thenReturn(optionalProduct);
		Product recover = productService.read(key);

		assertTrue(recover.compareOtherCode(potentialCode));
	}

	@Test
	public void verifyCompareCodeReadWrong() {
		Long key = 2L;
		Optional<Product> optionalProduct = Optional.of(potentialProduct);
		Mockito.when(productRepository.findById(Mockito.any())).thenReturn(optionalProduct);
		Product recover = productService.read(key);

		assertFalse(recover.compareOtherCode(potentialCode + "t"));
	}
}
