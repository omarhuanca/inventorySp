package bo.umss.app.inventorySp.provider.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import bo.umss.app.inventorySp.business.provider.service.ProviderService;
import bo.umss.app.inventorySp.business.provider.service.impl.ProviderServiceImpl;

@Configuration
public class ProviderServiceTestConfig {

	@Bean
	@Primary
	@Qualifier("providerService")
	ProviderService getProviderService() {
		return new ProviderServiceImpl();
	}
}
