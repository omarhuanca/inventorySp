package bo.umss.app.inventorySp.business.stock.service;

import bo.umss.app.inventorySp.business.measurement.model.Measurement;
import bo.umss.app.inventorySp.business.stock.model.Stock;
import bo.umss.app.inventorySp.service.CrudService;

public interface StockService extends CrudService<Stock, Long> {

	Stock findById(Long potentialId);

	Stock findByValue(Integer potentialValue, Measurement potentialMeasurement);
}
