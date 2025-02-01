package bo.umss.app.inventorySp.business.coin.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import bo.umss.app.inventorySp.business.coin.model.Coin;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CoinDto {

	@NotNull
	private Long id;

	@NotBlank
	private String code;

	private CoinDto(Long id, String code) {
		this.id = id;
		this.code = code;
	}

	public CoinDto() {

	}

	public static CoinDto at(Long id, @NotBlank String code) {
		if (code.isEmpty())
			throw new RuntimeException(Coin.CODE_CAN_NOT_BE_BLANK);

		return new CoinDto(id, code);
	}

	public Long getId() {
		return id;
	}

	public String getCode() {
		return code;
	}
}
