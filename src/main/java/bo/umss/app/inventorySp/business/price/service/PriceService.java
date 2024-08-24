package bo.umss.app.inventorySp.business.price.service;

import bo.umss.app.inventorySp.business.price.model.Price;
import bo.umss.app.inventorySp.service.CrudService;

public interface PriceService extends CrudService<Price, Long> {

	Price findByCode(String potentialCode);

	boolean existsByCode(String potentialCode);
}
