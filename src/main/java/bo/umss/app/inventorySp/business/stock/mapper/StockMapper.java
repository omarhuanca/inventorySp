package bo.umss.app.inventorySp.business.stock.mapper;

import org.springframework.stereotype.Service;

import bo.umss.app.inventorySp.business.measurement.dto.MeasurementDto;
import bo.umss.app.inventorySp.business.measurement.mapper.MeasurementMapper;
import bo.umss.app.inventorySp.business.measurement.model.Measurement;
import bo.umss.app.inventorySp.business.measurement.service.MeasurementService;
import bo.umss.app.inventorySp.business.stock.dto.StockDto;
import bo.umss.app.inventorySp.business.stock.model.Stock;
import bo.umss.app.inventorySp.business.stock.service.StockService;
import bo.umss.app.inventorySp.mapper.IMapper;

@Service
public class StockMapper implements IMapper<Stock, StockDto> {

	private final StockService service;

	private final MeasurementMapper measurementMapper;

	private final MeasurementService measurementService;
	
	public StockMapper(StockService service, MeasurementMapper measurementMapper,
			MeasurementService measurementService) {
		this.service = service;
		this.measurementMapper = measurementMapper;
		this.measurementService = measurementService;
	}

	@Override
	public StockDto toDto(Stock entity) {
		MeasurementDto measurementDto = measurementMapper.toDto(entity.getMeasurement());
		return StockDto.at(entity.getId(), entity.getValue(), measurementDto);
	}

	@Override
	public Stock toEntity(StockDto dto, boolean isNew) {
		Measurement measurement = measurementService.findByCode(dto.getMeasurement().getCode());

		if (isNew) {
			return Stock.at(dto.getValue(), measurement);
		} else {
			Stock recover = service.findById(dto.getId());
			recover.setValue(dto.getValue());
			recover.setMeasurement(measurement);

			return recover;
		}
	}
}
