package bo.umss.app.inventorySp.changePrice.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import bo.umss.app.inventorySp.business.changePrice.service.ChangePriceService;
import bo.umss.app.inventorySp.business.changePrice.service.impl.ChangePriceServiceImpl;

@Configuration
public class ChangePriceServiceTestConfig {

	@Bean
	@Primary
	@Qualifier("changePriceService")
	ChangePriceService getChangePrice() {
		return new ChangePriceServiceImpl();
	}
}
