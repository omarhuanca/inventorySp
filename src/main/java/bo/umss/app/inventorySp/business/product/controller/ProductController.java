package bo.umss.app.inventorySp.business.product.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import bo.umss.app.inventorySp.business.product.dto.ProductDto;
import bo.umss.app.inventorySp.business.product.mapper.ProductMapper;
import bo.umss.app.inventorySp.business.product.model.Product;
import bo.umss.app.inventorySp.business.product.service.ProductService;
import bo.umss.app.inventorySp.controller.CrudController;
import bo.umss.app.inventorySp.exception.BadParamsException;
import bo.umss.app.inventorySp.exception.CrudException;
import bo.umss.app.inventorySp.exception.EntityNotFoundException;

@RestController
@RequestMapping("/v1/products")
public class ProductController implements CrudController<ProductDto> {

	@Autowired
	private ProductService service;

	@Autowired
	private ProductMapper mapper;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@Override
	public ProductDto create(@RequestBody @Valid ProductDto dto) {
		try {
			return mapper.toDto(service.create(mapper.toEntity(dto, true)));
		} catch (BadParamsException e) {
			throw new BadParamsException();
		}
	}

	@PutMapping
	@ResponseStatus(HttpStatus.OK)
	@Override
	public void update(@RequestBody @Valid ProductDto dto) {
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

	@DeleteMapping(value = "/{code}")
	@Override
	public void delete(@PathVariable("code") String code) {
		try {
			service.deleteByCode(code);
		} catch (EntityNotFoundException e) {
			throw new EntityNotFoundException(e.getMessage());

		} catch (CrudException e) {
			throw new CrudException(e.getMessage());

		} catch (Exception e) {
			throw new BadParamsException(e.getMessage());
		}
	}

	@GetMapping(value = "/{code}")
	@Override
	public ProductDto read(@PathVariable("code") String code) {
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
	public List<ProductDto> findAll() {
		try {
			List<ProductDto> list = new ArrayList<>();
			for (Product entity : service.findAll()) {
				list.add(mapper.toDto(entity));
			}

			return list;
		} catch (CrudException e) {
			throw new CrudException();
		}
	}

	@GetMapping(value = "/searchByDescription", params = { "criteria" })
	public List<ProductDto> searchByDescription(@RequestParam("criteria") String criteria) {
		try {

			List<ProductDto> list = new ArrayList<>();
			for (Product entity : service.searchByDescription(criteria)) {
				list.add(mapper.toDto(entity));
			}

			return list;
		} catch (NullPointerException e) {
			throw new BadParamsException();
		} catch (CrudException e) {
			throw new CrudException();
		} catch (EntityNotFoundException e) {
			throw new EntityNotFoundException();
		}
	}
}
