package bo.umss.app.inventorySp.business.provider.service;

import bo.umss.app.inventorySp.business.provider.model.Provider;
import bo.umss.app.inventorySp.service.CrudService;

public interface ProviderService extends CrudService<Provider, Long> {

	Provider findByName(String potentialName);

	boolean existsByName(String potentialName);
}
