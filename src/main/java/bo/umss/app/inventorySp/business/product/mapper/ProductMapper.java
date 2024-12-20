package bo.umss.app.inventorySp.business.product.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bo.umss.app.inventorySp.business.line.dto.LineDto;
import bo.umss.app.inventorySp.business.line.mapper.LineMapper;
import bo.umss.app.inventorySp.business.line.model.Line;
import bo.umss.app.inventorySp.business.line.service.LineService;
import bo.umss.app.inventorySp.business.price.dto.PriceDto;
import bo.umss.app.inventorySp.business.price.mapper.PriceMapper;
import bo.umss.app.inventorySp.business.price.model.Price;
import bo.umss.app.inventorySp.business.price.service.PriceService;
import bo.umss.app.inventorySp.business.product.dto.ProductDto;
import bo.umss.app.inventorySp.business.product.model.Product;
import bo.umss.app.inventorySp.business.product.service.ProductService;
import bo.umss.app.inventorySp.business.provider.dto.ProviderDto;
import bo.umss.app.inventorySp.business.provider.mapper.ProviderMapper;
import bo.umss.app.inventorySp.business.provider.model.Provider;
import bo.umss.app.inventorySp.business.provider.service.ProviderService;
import bo.umss.app.inventorySp.business.stock.dto.StockDto;
import bo.umss.app.inventorySp.business.stock.mapper.StockMapper;
import bo.umss.app.inventorySp.business.stock.model.Stock;
import bo.umss.app.inventorySp.business.stock.service.StockService;
import bo.umss.app.inventorySp.mapper.IMapper;

@Service
public class ProductMapper implements IMapper<Product, ProductDto> {

	@Autowired
	private StockMapper stockMapper;

	@Autowired
	private StockService stockService;

	@Autowired
	private PriceMapper priceMapper;

	@Autowired
	private PriceService priceService;

	@Autowired
	private LineMapper lineMapper;

	@Autowired
	private LineService lineService;

	@Autowired
	private ProviderMapper providerMapper;

	@Autowired
	private ProviderService providerService;

	@Autowired
	private ProductService service;

	@Override
	public ProductDto toDto(Product entity) {
		StockDto stockDto = stockMapper.toDto(entity.getStock());
		PriceDto priceCostDto = priceMapper.toDto(entity.getPriceCost());
		PriceDto priceSaleDto = priceMapper.toDto(entity.getPriceSale());
		LineDto lineDto = lineMapper.toDto(entity.getLine());
		ProviderDto providerDto = providerMapper.toDto(entity.getProvider());

		return ProductDto.at(entity.getCode(), entity.getDescription(), stockDto, priceCostDto, priceSaleDto, lineDto,
				providerDto);
	}

	@Override
	public Product toEntity(ProductDto dto, boolean isNew) {
		Stock stock = stockService.findById(dto.getStock().getId());
		Price priceCost = priceService.findById(dto.getPriceCost().getId());
		Price priceSale = priceService.findById(dto.getPriceSale().getId());
		Line line = lineService.findByName(dto.getLine().getName());
		Provider provider = providerService.findByName(dto.getProvider().getName());

		if (isNew) {
			return Product.at(dto.getCode(), dto.getDescription(), stock, priceCost, priceSale, line, provider);
		} else {
			Product recover = service.findByCode(dto.getCode());
			recover.setStock(stock);
			recover.setPriceCost(priceCost);
			recover.setPriceSale(priceSale);
			recover.setLine(line);
			recover.setProvider(provider);

			return recover;
		}
	}

}
