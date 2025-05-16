package bo.umss.app.inventorySp.business.price.mapper;

import org.springframework.stereotype.Service;

import bo.umss.app.inventorySp.business.coin.dto.CoinDto;
import bo.umss.app.inventorySp.business.coin.mapper.CoinMapper;
import bo.umss.app.inventorySp.business.coin.model.Coin;
import bo.umss.app.inventorySp.business.coin.service.CoinService;
import bo.umss.app.inventorySp.business.price.dto.PriceDto;
import bo.umss.app.inventorySp.business.price.model.Price;
import bo.umss.app.inventorySp.business.price.service.PriceService;
import bo.umss.app.inventorySp.mapper.IMapper;

@Service
public class PriceMapper implements IMapper<Price, PriceDto> {

	private final PriceService service;

	private final CoinMapper coinMapper;

	private final CoinService coinService;
	
	public PriceMapper(PriceService service, CoinMapper coinMapper, CoinService coinService) {
		this.service = service;
		this.coinMapper = coinMapper;
		this.coinService = coinService;
	}

	@Override
	public PriceDto toDto(Price entity) {
		CoinDto coinDto = coinMapper.toDto(entity.getCoin());
		return PriceDto.at(entity.getId(), entity.getValue(), coinDto);
	}

	@Override
	public Price toEntity(PriceDto dto, boolean isNew) {
		Coin coin = coinService.findByCode(dto.getCoin().getCode());

		if (isNew) {
			return Price.at(dto.getValue(), coin);
		} else {
			Price recover = service.findById(dto.getId());
			recover.setValue(dto.getValue());
			recover.setCoin(coin);

			return recover;
		}
	}
}
