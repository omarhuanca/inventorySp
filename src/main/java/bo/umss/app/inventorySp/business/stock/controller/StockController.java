package bo.umss.app.inventorySp.business.stock.controller;

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

import bo.umss.app.inventorySp.business.stock.dto.StockDto;
import bo.umss.app.inventorySp.business.stock.mapper.StockMapper;
import bo.umss.app.inventorySp.business.stock.model.Stock;
import bo.umss.app.inventorySp.business.stock.service.StockService;
import bo.umss.app.inventorySp.controller.CrudController;
import bo.umss.app.inventorySp.exception.BadParamsException;
import bo.umss.app.inventorySp.exception.CrudException;
import bo.umss.app.inventorySp.exception.EntityNotFoundException;

@RestController
@RequestMapping("/v1/stocks")
public class StockController implements CrudController<StockDto> {

	private final StockService service;

	private final StockMapper mapper;

	public StockController(StockService service, StockMapper mapper) {
		this.service = service;
		this.mapper = mapper;
	}

	@Override
	public StockDto create(StockDto dto) {
		return null;
	}

	@PutMapping
	@ResponseStatus(HttpStatus.OK)
	@Override
	public void update(@RequestBody @Valid StockDto dto) {
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

	@GetMapping(value = "/readyById/{id}")
	public StockDto readById(@PathVariable("id") Long id) {
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
	public List<StockDto> findAll() {
		try {
			List<StockDto> entityList = new ArrayList<>();
			for (Stock entity : service.findAll()) {
				entityList.add(mapper.toDto(entity));
			}

			return entityList;
		} catch (CrudException e) {
			throw new CrudException();
		}
	}

	@Override
	public StockDto read(String code) {
		return null;
	}
}
