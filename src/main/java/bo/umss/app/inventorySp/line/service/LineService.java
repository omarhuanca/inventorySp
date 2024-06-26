package bo.umss.app.inventorySp.line.service;

import bo.umss.app.inventorySp.line.model.Line;
import bo.umss.app.inventorySp.service.CrudService;

public interface LineService extends CrudService<Line, Long> {

	Line findByName(String potentialName);

	boolean existsByName(String potentialName);
}
