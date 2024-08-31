package bo.umss.app.inventorySp.business.provider.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import bo.umss.app.inventorySp.business.product.repository.ProviderRepository;
import bo.umss.app.inventorySp.business.provider.model.Provider;
import bo.umss.app.inventorySp.business.provider.service.ProviderService;
import bo.umss.app.inventorySp.exception.BadParamsException;
import bo.umss.app.inventorySp.exception.CrudException;
import bo.umss.app.inventorySp.exception.EmptyFieldException;
import bo.umss.app.inventorySp.exception.EntityNotFoundException;
import bo.umss.app.inventorySp.exception.UniqueViolationException;

@Service
public class ProviderServiceImpl implements ProviderService {

	private Logger log = LogManager.getLogger(getClass());

	@Autowired
	private ProviderRepository repository;

	@Override
	public Provider create(Provider entity) {
		if (existsByName(entity.getName())) {
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

	@Transactional
	@Override
	public Provider update(Provider entity) {
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
	public Provider read(Long key) {
		try {
			Optional<Provider> entityOptional = repository.findById(key);
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
	public List<Provider> findAll() {
		try {
			return repository.findAll();
		} catch (DataAccessException e) {
			log.error(e.getMessage(), e);
			throw new CrudException(CrudException.DATA_ACCESS);
		}
	}

	@Override
	public Provider findByName(String potentialName) {
		if (StringUtils.isBlank(potentialName)) {
			throw new EmptyFieldException(Provider.NAME_CAN_NOT_BE_BLANK);
		}

		try {
			Provider entity = repository.findByName(potentialName);
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
	public boolean existsByName(String potentialName) {
		if (StringUtils.isBlank(potentialName)) {
			throw new EmptyFieldException(Provider.NAME_CAN_NOT_BE_BLANK);
		}

		try {
			return repository.existsByName(potentialName);
		} catch (DataAccessException e) {
			log.error(e.getMessage(), e);
			throw new CrudException(CrudException.DATA_ACCESS);
		}
	}

}
