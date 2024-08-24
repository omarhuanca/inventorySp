package bo.umss.app.inventorySp.business.stock.mapper;

import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	private StockService service;

	@Autowired
	private MeasurementMapper measurementMapper;

	@Autowired
	private MeasurementService measurementService;

	@Override
	public StockDto toDto(Stock entity) {
		MeasurementDto measurementDto = measurementMapper.toDto(entity.getMeasurement());
		return StockDto.at(entity.getCode(), entity.getValue(), measurementDto);
	}

	@Override
	public Stock toEntity(StockDto dto, boolean isNew) {
		Measurement measurement = measurementService.findByCode(dto.getMeasurement().getCode());

		if(isNew) {
			return Stock.at(dto.getCode(), dto.getValue(), measurement);
		} else {
			Stock recover = service.findByCode(dto.getCode());
			recover.setValue(dto.getValue());
			recover.setMeasurement(measurement);
			
			return recover;
		}
	}
}
