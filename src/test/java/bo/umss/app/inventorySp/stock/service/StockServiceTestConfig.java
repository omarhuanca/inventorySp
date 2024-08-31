package bo.umss.app.inventorySp.stock.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import bo.umss.app.inventorySp.business.stock.service.StockService;
import bo.umss.app.inventorySp.business.stock.service.impl.StockServiceImpl;

@Configuration
public class StockServiceTestConfig {

	@Bean
	@Primary
	@Qualifier("stockService")
	StockService getStockService() {
		return new StockServiceImpl();
	}
}
