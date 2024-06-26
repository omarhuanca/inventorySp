package bo.umss.app.inventorySp.service;

import java.io.Serializable;
import java.util.List;

public interface CrudService<E, K extends Serializable> {

	E create(E entity);

	E update(E entity);

	void delete(K key);

	E read(K key);

	List<E> findAll();
}
