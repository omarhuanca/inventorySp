package bo.umss.app.inventorySp.business.coin.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import bo.umss.app.inventorySp.business.coin.model.Coin;
import bo.umss.app.inventorySp.business.coin.repository.CoinRepository;
import bo.umss.app.inventorySp.business.coin.service.CoinService;
import bo.umss.app.inventorySp.exception.BadParamsException;
import bo.umss.app.inventorySp.exception.CrudException;
import bo.umss.app.inventorySp.exception.EmptyFieldException;
import bo.umss.app.inventorySp.exception.EntityNotFoundException;
import bo.umss.app.inventorySp.exception.UniqueViolationException;

@Service
public class CoinServiceImpl implements CoinService {

	private Logger log = LogManager.getLogger(getClass());

	@Autowired
	private CoinRepository repository;

	@Override
	public Coin create(Coin entity) {
		if (existsByCode(entity.getCode())) {
			throw new UniqueViolationException(UniqueViolationException.DATA_DUPLICATE);
		}

		try {
			return repository.save(entity);
		} catch (DataIntegrityViolationException e) {
			throw new BadParamsException();
		} catch (DataAccessException e) {
			throw new CrudException(CrudException.DATA_ACCESS);
		}
	}

	@Override
	public Coin update(Coin entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long key) {
		// TODO Auto-generated method stub

	}

	@Override
	public Coin read(Long key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Coin> findAll() {
		try {
			return repository.findAll();
		} catch (DataAccessException e) {
			log.error(e.getMessage(), e);
			throw new CrudException(CrudException.DATA_ACCESS);
		}
	}

	@Override
	public Coin findByCode(String potentialCode) {
		if (StringUtils.isBlank(potentialCode)) {
			throw new EmptyFieldException(Coin.CODE_CAN_NOT_BE_BLANK);
		}

		try {
			Coin entity = repository.findByCode(potentialCode);
			if (null != entity) {
				return entity;
			} else {
				throw new EntityNotFoundException();
			}
		} catch (DataAccessException e) {
			log.error(e.getMessage(), e);
			throw new CrudException(CrudException.DATA_ACCESS);
		}
	}

	@Override
	public boolean existsByCode(String potentialCode) {
		if (StringUtils.isBlank(potentialCode)) {
			throw new EmptyFieldException(Coin.CODE_CAN_NOT_BE_BLANK);
		}

		try {
			return repository.existsByCode(potentialCode);
		} catch (DataAccessException e) {
			log.error(e.getMessage(), e);
			throw new CrudException(CrudException.DATA_ACCESS);
		}
	}
}
