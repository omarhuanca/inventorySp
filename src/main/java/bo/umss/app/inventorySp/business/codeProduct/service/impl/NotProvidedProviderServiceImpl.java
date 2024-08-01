package bo.umss.app.inventorySp.business.codeProduct.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import bo.umss.app.inventorySp.business.codeProduct.model.NotProvidedProvider;
import bo.umss.app.inventorySp.business.codeProduct.repository.NotProvidedProviderRepository;
import bo.umss.app.inventorySp.business.codeProduct.service.NotProvidedProviderService;
import bo.umss.app.inventorySp.exception.BadParamsException;
import bo.umss.app.inventorySp.exception.CrudException;
import bo.umss.app.inventorySp.exception.EmptyFieldException;
import bo.umss.app.inventorySp.exception.EntityNotFoundException;
import bo.umss.app.inventorySp.exception.UniqueViolationException;

@Service
public class NotProvidedProviderServiceImpl implements NotProvidedProviderService {

	private Logger log = LogManager.getLogger(getClass());

	@Autowired
	private NotProvidedProviderRepository repository;

	@Override
	public NotProvidedProvider create(NotProvidedProvider entity) {
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

	@Transactional
	@Override
	public NotProvidedProvider update(NotProvidedProvider entity) {
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
	public NotProvidedProvider read(Long key) {
		try {
			Optional<NotProvidedProvider> entityOptional = repository.findById(key);
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
	public List<NotProvidedProvider> findAll() {
		try {
			return repository.findAll();
		} catch (DataAccessException e) {
			log.error(e.getMessage(), e);
			throw new CrudException(CrudException.DATA_ACCESS);
		}
	}

	@Override
	public NotProvidedProvider findByCode(String potentialCode) {
		if (StringUtils.isBlank(potentialCode)) {
			throw new EmptyFieldException();
		}

		try {
			NotProvidedProvider entity = repository.findByCode(potentialCode);
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
		try {
			return repository.existsByCode(potentialCode);
		} catch (DataAccessException e) {
			log.error(e.getMessage(), e);
			throw new CrudException(CrudException.DATA_ACCESS);
		}
	}
}
