package bo.umss.app.inventorySp.business.coin.dto;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import bo.umss.app.inventorySp.business.coin.model.Coin;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CoinDto {

	@NotBlank
	private String code;

	private CoinDto(String code) {
		this.code = code;
	}

	public CoinDto() {

	}

	public static CoinDto at(@NotBlank String code) {
		if (code.isEmpty())
			throw new RuntimeException(Coin.CODE_CAN_NOT_BE_BLANK);

		return new CoinDto(code);
	}

	public String getCode() {
		return code;
	}
}
