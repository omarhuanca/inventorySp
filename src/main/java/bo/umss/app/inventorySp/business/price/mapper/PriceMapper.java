package bo.umss.app.inventorySp.business.price.mapper;

import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	private PriceService service;

	@Autowired
	private CoinMapper coinMapper;

	@Autowired
	private CoinService coinService;

	@Override
	public PriceDto toDto(Price entity) {
		CoinDto coinDto = coinMapper.toDto(entity.getCoin());
		return PriceDto.at(entity.getCode(), entity.getValue(), coinDto);
	}

	@Override
	public Price toEntity(PriceDto dto, boolean isNew) {
		Coin coin = coinService.findByCode(dto.getCoin().getCode());

		if (isNew) {
			return Price.at(dto.getCode(), dto.getValue(), coin);
		} else {
			Price recover = service.findByCode(dto.getCode());
			recover.setValue(dto.getValue());
			recover.setCoin(coin);

			return recover;
		}
	}
}
