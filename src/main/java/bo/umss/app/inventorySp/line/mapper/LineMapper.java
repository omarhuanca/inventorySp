package bo.umss.app.inventorySp.line.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bo.umss.app.inventorySp.line.dto.LineDto;
import bo.umss.app.inventorySp.line.model.Line;
import bo.umss.app.inventorySp.line.service.LineService;
import bo.umss.app.inventorySp.mapper.IMapper;

@Service
public class LineMapper implements IMapper<Line, LineDto> {

	@Autowired
	private LineService service;

	@Override
	public LineDto toDto(Line entity) {
		return new LineDto(entity.getName());
	}

	@Override
	public Line toEntity(LineDto dto, boolean isNew) {
		if (isNew) {
			return Line.at(dto.getName());
		} else {
			return service.findByName(dto.getName());
		}
	}
}
