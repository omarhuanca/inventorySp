package bo.umss.app.inventorySp.codeProduct.service;

import bo.umss.app.inventorySp.codeProduct.model.NotProvidedProvider;
import bo.umss.app.inventorySp.service.CrudService;

public interface NotProvidedProviderService extends CrudService<NotProvidedProvider, Long> {

	NotProvidedProvider findByCode(String potentialCode);

	boolean existsByCode(String potentialCode);
}
