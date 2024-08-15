package bo.umss.app.inventorySp.business.coin.service;

import bo.umss.app.inventorySp.business.coin.model.Coin;
import bo.umss.app.inventorySp.service.CrudService;

public interface CoinService extends CrudService<Coin, Long> {

	Coin findByCode(String potentialCode);

	boolean existsByCode(String potentialCode);
}
