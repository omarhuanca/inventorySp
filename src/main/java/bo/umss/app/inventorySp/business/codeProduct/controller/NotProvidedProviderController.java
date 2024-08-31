package bo.umss.app.inventorySp.business.codeProduct.controller;

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

import bo.umss.app.inventorySp.business.codeProduct.dto.NotProvidedProviderDto;
import bo.umss.app.inventorySp.business.codeProduct.mapper.NotProvidedProviderMapper;
import bo.umss.app.inventorySp.business.codeProduct.model.NotProvidedProvider;
import bo.umss.app.inventorySp.business.codeProduct.service.NotProvidedProviderService;
import bo.umss.app.inventorySp.controller.CrudController;
import bo.umss.app.inventorySp.exception.BadParamsException;
import bo.umss.app.inventorySp.exception.CrudException;
import bo.umss.app.inventorySp.exception.EntityNotFoundException;

@RestController
@RequestMapping("/v1/notprovidedproviders")
public class NotProvidedProviderController implements CrudController<NotProvidedProviderDto> {

	@Autowired
	private NotProvidedProviderService service;

	@Autowired
	private NotProvidedProviderMapper mapper;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@Override
	public NotProvidedProviderDto create(@RequestBody @Valid NotProvidedProviderDto dto) {
		try {
			return mapper.toDto(service.create(mapper.toEntity(dto, true)));
		} catch (BadParamsException e) {
			throw new BadParamsException();
		}
	}

	@PutMapping
	@ResponseStatus(HttpStatus.OK)
	@Override
	public void update(@RequestBody @Valid NotProvidedProviderDto dto) {
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
	public NotProvidedProviderDto read(@PathVariable("code") String code) {
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
	public List<NotProvidedProviderDto> findAll() {
		try {
			List<NotProvidedProviderDto> lineList = new ArrayList<>();
			for (NotProvidedProvider entity : service.findAll()) {
				lineList.add(mapper.toDto(entity));
			}

			return lineList;
		} catch (CrudException e) {
			throw new CrudException();
		}
	}
}
