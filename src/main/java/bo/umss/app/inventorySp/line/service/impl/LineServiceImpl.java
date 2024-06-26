package bo.umss.app.inventorySp.line.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import bo.umss.app.inventorySp.exception.BadParamException;
import bo.umss.app.inventorySp.exception.CrudException;
import bo.umss.app.inventorySp.exception.EmptyFieldException;
import bo.umss.app.inventorySp.exception.EntityNotFoundException;
import bo.umss.app.inventorySp.exception.UniqueViolationException;
import bo.umss.app.inventorySp.line.model.Line;
import bo.umss.app.inventorySp.line.repository.LineRepository;
import bo.umss.app.inventorySp.line.service.LineService;

@Service
public class LineServiceImpl implements LineService {

	private Logger log = LogManager.getLogger(getClass());

	@Autowired
	private LineRepository repository;

	@Override
	public Line create(Line entity) {
		if (existsByName(entity.getName())) {
			throw new UniqueViolationException(UniqueViolationException.DATA_DUPLICATE);
		}

		try {
			repository.save(entity);
			return entity;
		} catch (DataIntegrityViolationException e) {
			throw new BadParamException();
		} catch (DataAccessException e) {
			throw new CrudException(CrudException.DATA_ACCESS);
		}
	}

	@Transactional
	@Override
	public Line update(Line entity) {
		try {
			return repository.save(entity);
		} catch (ConstraintViolationException e) {
			throw new BadParamException(e.getMessage());
		} catch (DataAccessException e) {
			throw new CrudException(CrudException.DATA_ACCESS);
		}
	}

	@Override
	public void delete(Long key) {
		// TODO Auto-generated method stub

	}

	@Override
	public Line read(Long key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Line> findAll() {
		try {
			return repository.findAll();
		} catch (DataAccessException e) {
			log.error(e.getMessage(), e);
			throw new CrudException("");
		}
	}

	@Override
	public Line findByName(String potentialName) {

		if (StringUtils.isBlank(potentialName)) {
			throw new EmptyFieldException();
		}

		try {
			Line entity = repository.findByName(potentialName);
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
		try {
			return repository.existsByName(potentialName);
		} catch (DataAccessException e) {
			log.error(e.getMessage(), e);
			throw new CrudException("");
		}
	}
}
