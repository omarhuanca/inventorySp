package bo.umss.app.inventorySp.business.measurement.mapper;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import bo.umss.app.inventorySp.business.measurement.dto.MeasurementDto;
import bo.umss.app.inventorySp.business.measurement.model.Measurement;
import bo.umss.app.inventorySp.business.measurement.service.MeasurementService;
import bo.umss.app.inventorySp.mapper.IMapper;

@Service
public class MeasurementMapper implements IMapper<Measurement, MeasurementDto> {

	private final MeasurementService service;

	public MeasurementMapper(MeasurementService service) {
		this.service = service;
	}

	@Override
	public MeasurementDto toDto(Measurement entity) {
		return MeasurementDto.at(entity.getId(), entity.getCode());
	}

	@Override
	public Measurement toEntity(MeasurementDto dto, boolean isNew) {
		if (isNew) {
			return Measurement.at(dto.getCode());
		} else {
			Measurement recover = service.findById(dto.getId());
			recover.setCode(StringUtils.upperCase(dto.getCode()));

			return recover;
		}
	}
}
