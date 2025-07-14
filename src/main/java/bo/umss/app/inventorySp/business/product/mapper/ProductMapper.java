package bo.umss.app.inventorySp.business.product.mapper;

import java.util.Base64;

import org.springframework.stereotype.Service;

import bo.umss.app.inventorySp.business.coin.dto.CoinDto;
import bo.umss.app.inventorySp.business.coin.mapper.CoinMapper;
import bo.umss.app.inventorySp.business.coin.model.Coin;
import bo.umss.app.inventorySp.business.coin.service.CoinService;
import bo.umss.app.inventorySp.business.line.dto.LineDto;
import bo.umss.app.inventorySp.business.line.mapper.LineMapper;
import bo.umss.app.inventorySp.business.line.model.Line;
import bo.umss.app.inventorySp.business.line.service.LineService;
import bo.umss.app.inventorySp.business.measurement.dto.MeasurementDto;
import bo.umss.app.inventorySp.business.measurement.mapper.MeasurementMapper;
import bo.umss.app.inventorySp.business.measurement.model.Measurement;
import bo.umss.app.inventorySp.business.measurement.service.MeasurementService;
import bo.umss.app.inventorySp.business.product.dto.ProductDto;
import bo.umss.app.inventorySp.business.product.model.Product;
import bo.umss.app.inventorySp.business.product.service.ProductService;
import bo.umss.app.inventorySp.business.provider.dto.ProviderDto;
import bo.umss.app.inventorySp.business.provider.mapper.ProviderMapper;
import bo.umss.app.inventorySp.business.provider.model.Provider;
import bo.umss.app.inventorySp.business.provider.service.ProviderService;
import bo.umss.app.inventorySp.exception.BadParamsException;
import bo.umss.app.inventorySp.mapper.IMapper;

@Service
public class ProductMapper implements IMapper<Product, ProductDto> {

	private final ProductService service;

	private final MeasurementMapper measurementMapper;

	private final MeasurementService measurementService;

	private final CoinMapper coinMapper;

	private final CoinService coinService;

	private final LineMapper lineMapper;

	private final LineService lineService;

	private final ProviderMapper providerMapper;

	private final ProviderService providerService;

	public ProductMapper(ProductService service, MeasurementMapper measurementMapper,
			MeasurementService measurementService, CoinMapper coinMapper, CoinService coinService,
			LineMapper lineMapper, LineService lineService, ProviderMapper providerMapper,
			ProviderService providerService) {
		this.service = service;
		this.measurementMapper = measurementMapper;
		this.measurementService = measurementService;
		this.coinMapper = coinMapper;
		this.coinService = coinService;
		this.lineMapper = lineMapper;
		this.lineService = lineService;
		this.providerMapper = providerMapper;
		this.providerService = providerService;
	}

	@Override
	public ProductDto toDto(Product entity) {
		MeasurementDto measurementDto = measurementMapper.toDto(entity.getMeasurement());
		CoinDto coinDto = coinMapper.toDto(entity.getCoin());
		String stringImage = Base64.getEncoder().encodeToString(entity.getImage());
		LineDto lineDto = lineMapper.toDto(entity.getLine());
		ProviderDto providerDto = providerMapper.toDto(entity.getProvider());

		return ProductDto.at(entity.getCode(), entity.getDescription(), entity.getStock(), measurementDto,
				entity.getPriceCost(), entity.getPriceSale(), coinDto, stringImage, lineDto, providerDto);
	}

	@Override
	public Product toEntity(ProductDto dto, boolean isNew) {
		Integer stock = dto.getStock();
		Measurement measurement = measurementService.findByCode(dto.getMeasurement().getCode());
		Coin coin = coinService.findByCode(dto.getCoin().getCode());

		byte[] imageByte = new byte[0];

		if (dto.getImageBase64() != null && !dto.getImageBase64().isEmpty()) {
			try {
				imageByte = Base64.getDecoder().decode(dto.getImageBase64());
			} catch (IllegalArgumentException e) {
				throw new BadParamsException("Invalid base64 image");
			}
		}

		Line line = lineService.findByName(dto.getLine().getName());
		Provider provider = providerService.findByName(dto.getProvider().getName());

		if (isNew) {
			return Product.at(dto.getCode(), dto.getDescription(), stock, measurement, dto.getPriceCost(),
					dto.getPriceSale(), coin, imageByte, line, provider);
		} else {
			Product recover = service.findByCode(dto.getCode());
			recover.setDescription(dto.getDescription());
			recover.setStock(stock);
			recover.setMeasurement(measurement);
			recover.setPriceCost(dto.getPriceCost());
			recover.setPriceSale(dto.getPriceSale());
			recover.setImage(imageByte);
			recover.setCoin(coin);
			recover.setLine(line);
			recover.setProvider(provider);

			return recover;
		}
	}
}
