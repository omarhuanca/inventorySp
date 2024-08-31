package bo.umss.app.inventorySp.business.codeProduct.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bo.umss.app.inventorySp.business.codeProduct.dto.NotProvidedProviderDto;
import bo.umss.app.inventorySp.business.codeProduct.model.NotProvidedProvider;
import bo.umss.app.inventorySp.business.codeProduct.service.NotProvidedProviderService;
import bo.umss.app.inventorySp.business.line.dto.LineDto;
import bo.umss.app.inventorySp.business.line.mapper.LineMapper;
import bo.umss.app.inventorySp.business.line.model.Line;
import bo.umss.app.inventorySp.business.line.service.LineService;
import bo.umss.app.inventorySp.mapper.IMapper;

@Service
public class NotProvidedProviderMapper implements IMapper<NotProvidedProvider, NotProvidedProviderDto> {

	@Autowired
	private NotProvidedProviderService service;

	@Autowired
	private LineMapper lineMapper;

	@Autowired
	private LineService lineService;

	@Override
	public NotProvidedProviderDto toDto(NotProvidedProvider entity) {
		LineDto line = lineMapper.toDto(entity.getLine());
		return NotProvidedProviderDto.at(entity.getCode(), entity.getDescription(), line);
	}

	@Override
	public NotProvidedProvider toEntity(NotProvidedProviderDto dto, boolean isNew) {
		Line line = lineService.findByName(dto.getLine().getName());

		if (isNew) {
			return NotProvidedProvider.at(dto.getCode(), dto.getDescription(), line);
		} else {
			NotProvidedProvider recover = service.findByCode(dto.getCode());
			recover.setDescription(dto.getDescription());
			recover.setLine(line);

			return recover;
		}
	}
}
