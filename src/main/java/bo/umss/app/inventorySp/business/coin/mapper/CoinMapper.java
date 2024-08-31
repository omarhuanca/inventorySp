package bo.umss.app.inventorySp.business.coin.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bo.umss.app.inventorySp.business.coin.dto.CoinDto;
import bo.umss.app.inventorySp.business.coin.model.Coin;
import bo.umss.app.inventorySp.business.coin.service.CoinService;
import bo.umss.app.inventorySp.mapper.IMapper;

@Service
public class CoinMapper implements IMapper<Coin, CoinDto> {

	@Autowired
	private CoinService service;

	@Override
	public CoinDto toDto(Coin entity) {
		return CoinDto.at(entity.getCode());
	}

	@Override
	public Coin toEntity(CoinDto dto, boolean isNew) {
		if (isNew) {
			return Coin.at(dto.getCode());
		} else {
			return service.findByCode(dto.getCode());
		}
	}

}
