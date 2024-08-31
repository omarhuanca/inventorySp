package bo.umss.app.inventorySp.business.measurement.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import bo.umss.app.inventorySp.business.measurement.model.Measurement;
import bo.umss.app.inventorySp.business.measurement.repository.MeasurementRepository;
import bo.umss.app.inventorySp.business.measurement.service.MeasurementService;
import bo.umss.app.inventorySp.exception.BadParamsException;
import bo.umss.app.inventorySp.exception.CrudException;
import bo.umss.app.inventorySp.exception.EmptyFieldException;
import bo.umss.app.inventorySp.exception.EntityNotFoundException;
import bo.umss.app.inventorySp.exception.UniqueViolationException;

@Service
public class MeasurementServiceImpl implements MeasurementService {

	private Logger log = LogManager.getLogger(getClass());

	@Autowired
	private MeasurementRepository repository;

	@Override
	public Measurement create(Measurement entity) {
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
	public Measurement update(Measurement entity) {
		return null;
	}

	@Override
	public void delete(Long key) {
		// TODO Auto-generated method stub

	}

	@Override
	public Measurement read(Long key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Measurement> findAll() {
		try {
			return repository.findAll();
		} catch (DataAccessException e) {
			log.error(e.getMessage(), e);
			throw new CrudException(CrudException.DATA_ACCESS);
		}
	}

	@Override
	public Measurement findByCode(String potentialCode) {
		if (StringUtils.isBlank(potentialCode)) {
			throw new EmptyFieldException(Measurement.CODE_CAN_NOT_BE_BLANK);
		}

		try {
			Measurement entity = repository.findByCode(potentialCode);
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
			throw new EmptyFieldException(Measurement.CODE_CAN_NOT_BE_BLANK);
		}

		try {
			return repository.existsByCode(potentialCode);
		} catch (DataAccessException e) {
			log.error(e.getMessage(), e);
			throw new CrudException(CrudException.DATA_ACCESS);
		}
	}
}
