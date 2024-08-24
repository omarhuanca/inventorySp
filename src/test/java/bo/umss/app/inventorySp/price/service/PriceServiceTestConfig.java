package bo.umss.app.inventorySp.price.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import bo.umss.app.inventorySp.business.price.service.PriceService;
import bo.umss.app.inventorySp.business.price.service.impl.PriceServiceImpl;

@Configuration
public class PriceServiceTestConfig {

	@Bean
	@Primary
	@Qualifier("priceService")
	PriceService getPrice() {
		return new PriceServiceImpl();
	}
}
