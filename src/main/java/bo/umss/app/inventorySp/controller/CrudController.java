package bo.umss.app.inventorySp.controller;

import java.util.List;

public interface CrudController<D> {

	D create(D dto);

	void update(D dto);

	void delete(String code);

	D read(String code);

	List<D> findAll();
}
