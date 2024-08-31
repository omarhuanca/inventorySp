package bo.umss.app.inventorySp.coin.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import bo.umss.app.inventorySp.business.coin.service.CoinService;
import bo.umss.app.inventorySp.business.coin.service.impl.CoinServiceImpl;

public class CoinServiceTestConfig {

	@Bean
	@Primary
	@Qualifier("coinService")
	CoinService getCoinService() {
		return new CoinServiceImpl();
	}
}
