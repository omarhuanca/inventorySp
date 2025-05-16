package bo.umss.app.inventorySp.business.price.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import bo.umss.app.inventorySp.business.price.dto.PriceDto;
import bo.umss.app.inventorySp.business.price.mapper.PriceMapper;
import bo.umss.app.inventorySp.business.price.model.Price;
import bo.umss.app.inventorySp.business.price.service.PriceService;
import bo.umss.app.inventorySp.controller.CrudController;
import bo.umss.app.inventorySp.exception.BadParamsException;
import bo.umss.app.inventorySp.exception.CrudException;
import bo.umss.app.inventorySp.exception.EntityNotFoundException;

@RestController
@RequestMapping("/v1/prices")
public class PriceController implements CrudController<PriceDto> {

	private final PriceService service;

	private final PriceMapper mapper;
	
	public PriceController(PriceService service, PriceMapper mapper) {
		this.service = service;
		this.mapper = mapper;
	}

	@Override
	public PriceDto create(PriceDto dto) {
		return null;
	}

	@PutMapping
	@ResponseStatus(HttpStatus.OK)
	@Override
	public void update(@RequestBody @Valid PriceDto dto) {
		try {
			service.update(mapper.toEntity(dto, false));

		} catch (NullPointerException e) {
			throw new BadParamsException();

		} catch (CrudException e) {
			throw new CrudException(e.getMessage());

		} catch (EntityNotFoundException e) {
			throw new EntityNotFoundException();
		}
	}

	@Override
	public void delete(String code) {
		// TODO Auto-generated method stub

	}

	@GetMapping(value = "/readById/{id}")
	public PriceDto readById(@PathVariable("id") Long id) {
		try {
			return mapper.toDto(service.findById(id));
		} catch (NullPointerException e) {
			throw new BadParamsException();
		} catch (CrudException e) {
			throw new CrudException();
		} catch (EntityNotFoundException e) {
			throw new EntityNotFoundException();
		}
	}

	@GetMapping
	@Override
	public List<PriceDto> findAll() {
		try {
			List<PriceDto> entityList = new ArrayList<>();
			for (Price entity : service.findAll()) {
				entityList.add(mapper.toDto(entity));
			}

			return entityList;
		} catch (CrudException e) {
			throw new CrudException();
		}
	}

	@Override
	public PriceDto read(String code) {
		return null;
	}
}
