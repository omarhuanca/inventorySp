package bo.umss.app.inventorySp.business.stock.service;

import bo.umss.app.inventorySp.business.stock.model.Stock;
import bo.umss.app.inventorySp.service.CrudService;

public interface StockService extends CrudService<Stock, Long> {

	Stock findByCode(String potentialCode);

	boolean existsByCode(String potentialCode);
}
