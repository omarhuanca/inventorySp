package bo.umss.app.inventorySp.product.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import bo.umss.app.inventorySp.business.product.service.ProductService;
import bo.umss.app.inventorySp.business.product.service.impl.ProductServiceImpl;

@Configuration
public class ProductServiceTestConfig {

	@Bean
	@Primary
	@Qualifier("productService")
	ProductService getProductService() {
		return new ProductServiceImpl();
	}
}
