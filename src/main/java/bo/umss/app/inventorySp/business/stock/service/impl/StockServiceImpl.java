package bo.umss.app.inventorySp.business.stock.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import bo.umss.app.inventorySp.business.stock.model.Stock;
import bo.umss.app.inventorySp.business.stock.repository.StockRepository;
import bo.umss.app.inventorySp.business.stock.service.StockService;
import bo.umss.app.inventorySp.exception.BadParamsException;
import bo.umss.app.inventorySp.exception.CrudException;
import bo.umss.app.inventorySp.exception.EntityNotFoundException;
import bo.umss.app.inventorySp.exception.NegativeFieldException;

@Service
public class StockServiceImpl implements StockService {

	private Logger log = LogManager.getLogger(getClass());

	@Autowired
	private StockRepository repository;

	@Override
	public Stock create(Stock entity) {
		if (entity.verifyValueIsNegative()) {
			throw new NegativeFieldException(Stock.VALUE_CAN_NOT_BE_LESS_THAN_ZERO);
		}

		try {
			return repository.save(entity);
		} catch (DataIntegrityViolationException e) {
			throw new BadParamsException();
		} catch (DataAccessException e) {
			throw new CrudException(CrudException.DATA_ACCESS);
		}
	}

	@Transactional
	@Override
	public Stock update(Stock entity) {
		if (entity.verifyValueIsNegative()) {
			throw new NegativeFieldException(Stock.VALUE_CAN_NOT_BE_LESS_THAN_ZERO);
		}

		try {
			return repository.save(entity);
		} catch (ConstraintViolationException e) {
			throw new BadParamsException(e.getMessage());
		} catch (DataAccessException e) {
			throw new CrudException(CrudException.DATA_ACCESS);
		}
	}

	@Override
	public void delete(Long key) {
		// TODO Auto-generated method stub

	}

	@Override
	public Stock read(Long key) {
		try {
			Optional<Stock> entityOptional = repository.findById(key);
			if (!entityOptional.isPresent()) {
				throw new EntityNotFoundException();
			}

			return entityOptional.get();
		} catch (DataAccessException e) {
			log.error(e.getMessage(), e);
			throw new CrudException(CrudException.DATA_ACCESS);
		}
	}

	@Override
	public List<Stock> findAll() {
		try {
			return repository.findAll();
		} catch (DataAccessException e) {
			log.error(e.getMessage(), e);
			throw new CrudException(CrudException.DATA_ACCESS);
		}
	}

	@Override
	public Stock findById(Long potentialId) {
		try {
			Stock entity = repository.findById(potentialId).orElseThrow(() -> new EntityNotFoundException());

			return entity;
		} catch (DataAccessException e) {
			log.error(e.getMessage(), e);
			throw new CrudException(CrudException.DATA_ACCESS);
		}
	}

	public boolean existsById(Long potentialId) {
		if (potentialId < 0) {
			throw new NegativeFieldException(Stock.ID_CAN_NOT_BE_LESS_THAN_ZERO);
		}

		try {
			return repository.existsById(potentialId);
		} catch (DataAccessException e) {
			log.error(e.getMessage(), e);
			throw new CrudException(CrudException.DATA_ACCESS);
		}
	}
}
