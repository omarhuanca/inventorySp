package bo.umss.app.inventorySp.business.measurement.service;

import bo.umss.app.inventorySp.business.measurement.model.Measurement;
import bo.umss.app.inventorySp.service.CrudService;

public interface MeasurementService extends CrudService<Measurement, Long> {

	Measurement findByCode(String potentialCode);

	boolean existsByCode(String potentialCode);
}
