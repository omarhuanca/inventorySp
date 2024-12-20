package bo.umss.app.inventorySp.business.price.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import bo.umss.app.inventorySp.business.coin.dto.CoinDto;
import bo.umss.app.inventorySp.business.price.model.Price;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PriceDto {

	@NotNull
	private Long id;

	@NotNull
	private Double value;

	@NotNull
	private CoinDto coin;

	public PriceDto(Long id, double value, CoinDto coin) {
		this.id = id;
		this.value = value;
		this.coin = coin;
	}

	public PriceDto() {
	}

	public static PriceDto at(Long id, @NotNull Double value, @NotNull CoinDto coin) {
		if (0 >= value)
			throw new RuntimeException(Price.VALUE_CAN_NOT_BE_LESS_ZERO);
		if (null == coin)
			throw new RuntimeException(Price.COIN_CAN_NOT_BE_NULL);

		return new PriceDto(id, value, coin);
	}

	public Long getId() {
		return id;
	}

	public Double getValue() {
		return value;
	}

	public CoinDto getCoin() {
		return coin;
	}
}
