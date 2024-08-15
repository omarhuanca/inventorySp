package bo.umss.app.inventorySp.business.measurement.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bo.umss.app.inventorySp.business.measurement.dto.MeasurementDto;
import bo.umss.app.inventorySp.business.measurement.model.Measurement;
import bo.umss.app.inventorySp.business.measurement.service.MeasurementService;
import bo.umss.app.inventorySp.mapper.IMapper;

@Service
public class MeasurementMapper implements IMapper<Measurement, MeasurementDto> {

	@Autowired
	private MeasurementService service;

	@Override
	public MeasurementDto toDto(Measurement entity) {
		return MeasurementDto.at(entity.getCode());
	}

	@Override
	public Measurement toEntity(MeasurementDto dto, boolean isNew) {
		if (isNew) {
			return Measurement.at(dto.getCode());
		} else {
			return service.findByCode(dto.getCode());
		}
	}
}
