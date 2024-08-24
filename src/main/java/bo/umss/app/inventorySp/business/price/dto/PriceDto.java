package bo.umss.app.inventorySp.business.price.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import bo.umss.app.inventorySp.business.coin.dto.CoinDto;
import bo.umss.app.inventorySp.business.price.model.Price;
import bo.umss.app.inventorySp.exception.EmptyFieldException;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PriceDto {

	@NotNull
	private String code;

	@NotNull
	private Double value;

	@NotNull
	private CoinDto coin;

	public PriceDto(String code, double value, CoinDto coin) {
		this.code = code;
		this.value = value;
		this.coin = coin;
	}

	public PriceDto() {
	}

	public static PriceDto at(@NotNull String code, @NotNull Double value, @NotNull CoinDto coin) {
		if (code.isEmpty())
			throw new EmptyFieldException(Price.CODE_CAN_NOT_BE_BLANK);
		if (0 >= value)
			throw new RuntimeException(Price.VALUE_CAN_NOT_BE_LESS_ZERO);
		if (null == coin)
			throw new RuntimeException(Price.COIN_CAN_NOT_BE_NULL);

		return new PriceDto(code, value, coin);
	}

	public String getCode() {
		return code;
	}

	public Double getValue() {
		return value;
	}

	public CoinDto getCoin() {
		return coin;
	}
}
