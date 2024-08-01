package bo.umss.app.inventorySp.business.codeProduct.service;

import bo.umss.app.inventorySp.business.codeProduct.model.NotProvidedProvider;
import bo.umss.app.inventorySp.service.CrudService;

public interface NotProvidedProviderService extends CrudService<NotProvidedProvider, Long> {

	NotProvidedProvider findByCode(String potentialCode);

	boolean existsByCode(String potentialCode);
}
