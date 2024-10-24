package bo.umss.app.inventorySp.business.product.service;

import bo.umss.app.inventorySp.business.product.model.Product;
import bo.umss.app.inventorySp.service.CrudService;

public interface ProductService extends CrudService<Product, Long> {

	Product findByCode(String potentialCode);

	boolean existsByCode(String potentialCode);
}
