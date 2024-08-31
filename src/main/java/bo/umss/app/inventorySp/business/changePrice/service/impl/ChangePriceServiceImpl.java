package bo.umss.app.inventorySp.business.changePrice.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import bo.umss.app.inventorySp.business.changePrice.model.ChangePrice;
import bo.umss.app.inventorySp.business.changePrice.repository.ChangePriceRepository;
import bo.umss.app.inventorySp.business.changePrice.service.ChangePriceService;
import bo.umss.app.inventorySp.exception.BadParamsException;
import bo.umss.app.inventorySp.exception.CrudException;

@Service
public class ChangePriceServiceImpl implements ChangePriceService {

	private Logger log = LogManager.getLogger(getClass());

	@Autowired
	private ChangePriceRepository repository;

	@Override
	public ChangePrice create(ChangePrice entity) {
		try {
			return repository.save(entity);
		} catch (DataIntegrityViolationException e) {
			throw new BadParamsException();
		} catch (DataAccessException e) {
			throw new CrudException(CrudException.DATA_ACCESS);
		}
	}

	@Override
	public ChangePrice update(ChangePrice entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long key) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ChangePrice read(Long key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ChangePrice> findAll() {
		try {
			return repository.findAll();
		} catch (DataAccessException e) {
			log.error(e.getMessage(), e);
			throw new CrudException(CrudException.DATA_ACCESS);
		}
	}

}
