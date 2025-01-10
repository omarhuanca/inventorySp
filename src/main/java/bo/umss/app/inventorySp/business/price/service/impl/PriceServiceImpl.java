package bo.umss.app.inventorySp.business.price.service.impl;

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

import bo.umss.app.inventorySp.business.coin.model.Coin;
import bo.umss.app.inventorySp.business.price.model.Price;
import bo.umss.app.inventorySp.business.price.repository.PriceRepository;
import bo.umss.app.inventorySp.business.price.service.PriceService;
import bo.umss.app.inventorySp.exception.BadParamsException;
import bo.umss.app.inventorySp.exception.CrudException;
import bo.umss.app.inventorySp.exception.EntityNotFoundException;
import bo.umss.app.inventorySp.exception.NegativeFieldException;

@Service
public class PriceServiceImpl implements PriceService {

	private Logger log = LogManager.getLogger(getClass());

	@Autowired
	private PriceRepository repository;

	@Override
	public Price create(Price entity) {
		if (entity.isNegativeValue()) {
			throw new NegativeFieldException(Price.VALUE_CAN_NOT_BE_LESS_ZERO);
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
	public Price update(Price entity) {
		if (entity.isNegativeValue()) {
			throw new NegativeFieldException(Price.VALUE_CAN_NOT_BE_LESS_ZERO);
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
	public Price read(Long key) {
		try {
			Optional<Price> entityOptional = repository.findById(key);
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
	public List<Price> findAll() {
		try {
			return repository.findAll();
		} catch (DataAccessException e) {
			log.error(e.getMessage(), e);
			throw new CrudException(CrudException.DATA_ACCESS);
		}
	}

	public Price findById(Long potentialId) {
		try {
			Price entity = repository.findById(potentialId).orElseThrow(() -> new EntityNotFoundException());

			return entity;
		} catch (DataAccessException e) {
			log.error(e.getMessage(), e);
			throw new CrudException(CrudException.DATA_ACCESS);
		}
	}

	public boolean existsById(Long potentialId) {
		if (potentialId < 0) {
			throw new NegativeFieldException(Price.ID_CAN_NOT_BE_BLANK);
		}

		try {
			return repository.existsById(potentialId);
		} catch (DataAccessException e) {
			log.error(e.getMessage(), e);
			throw new CrudException(CrudException.DATA_ACCESS);
		}
	}

	@Override
	public Price findByValue(Double potentialValue, Coin potentialCoin) {
		
		if (0 > potentialValue) {
			throw new NegativeFieldException(Price.VALUE_CAN_NOT_BE_LESS_ZERO);
		}

		try {
			Optional<Price> entityOptional = repository.findByValue(potentialValue);
			if(entityOptional.isPresent()) {
				return entityOptional.get();
			} else {
				return repository.save(Price.at(potentialValue, potentialCoin));				
			}

		} catch (DataAccessException e) {
			log.error(e.getMessage(), e);
			throw new CrudException(CrudException.DATA_ACCESS);
		}
	}
}
