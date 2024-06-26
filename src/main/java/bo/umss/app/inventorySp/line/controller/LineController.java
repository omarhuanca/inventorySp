package bo.umss.app.inventorySp.line.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import bo.umss.app.inventorySp.controller.CrudController;
import bo.umss.app.inventorySp.exception.BadParamException;
import bo.umss.app.inventorySp.exception.CrudException;
import bo.umss.app.inventorySp.exception.EntityNotFoundException;
import bo.umss.app.inventorySp.line.dto.LineDto;
import bo.umss.app.inventorySp.line.mapper.LineMapper;
import bo.umss.app.inventorySp.line.model.Line;
import bo.umss.app.inventorySp.line.service.LineService;

@RestController
@RequestMapping("/api/v1/lines")
public class LineController implements CrudController<LineDto> {

	@Autowired
	private LineService service;

	@Autowired
	private LineMapper mapper;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@Override
	public LineDto create(@RequestBody @Valid LineDto dto) {
		try {
			return mapper.toDto(service.create(mapper.toEntity(dto, Boolean.TRUE)));
		} catch (BadParamException e) {
			throw new BadParamException();
		}
	}

	@Override
	public void update(LineDto dto) {

	}

	@Override
	public void delete(String code) {
		// TODO Auto-generated method stub

	}

	@GetMapping(value = "/{code}")
	@Override
	public LineDto read(@PathVariable("code") String code) {
		try {
			return mapper.toDto(service.findByName(code));
		} catch (NullPointerException e) {
			throw new BadParamException();
		} catch (CrudException e) {
			throw new CrudException();
		} catch (EntityNotFoundException e) {
			throw new EntityNotFoundException();
		}
	}

	@GetMapping
	@Override
	public List<LineDto> findAll() {
		try {
			List<LineDto> lineList = new ArrayList<>();
			for (Line line : service.findAll()) {
				lineList.add(mapper.toDto(line));
			}

			return lineList;
		} catch (CrudException e) {
			throw new CrudException();
		}
	}
}
