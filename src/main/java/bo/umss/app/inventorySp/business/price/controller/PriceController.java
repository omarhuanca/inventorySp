package bo.umss.app.inventorySp.business.price.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

	@Autowired
	private PriceService service;

	@Autowired
	private PriceMapper mapper;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@Override
	public PriceDto create(@RequestBody @Valid PriceDto dto) {
		try {
			return mapper.toDto(service.create(mapper.toEntity(dto, true)));
		} catch (BadParamsException e) {
			throw new BadParamsException();
		}
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

	@GetMapping(value = "/{code}")
	@Override
	public PriceDto read(@PathVariable("code") String code) {
		try {
			return mapper.toDto(service.findByCode(code));
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
			List<PriceDto> priceList = new ArrayList<>();
			for (Price entity : service.findAll()) {
				priceList.add(mapper.toDto(entity));
			}

			return priceList;
		} catch (CrudException e) {
			throw new CrudException();
		}
	}
}
