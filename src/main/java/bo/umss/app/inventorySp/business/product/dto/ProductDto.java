package bo.umss.app.inventorySp.business.product.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import bo.umss.app.inventorySp.business.coin.dto.CoinDto;
import bo.umss.app.inventorySp.business.line.dto.LineDto;
import bo.umss.app.inventorySp.business.measurement.dto.MeasurementDto;
import bo.umss.app.inventorySp.business.product.model.Product;
import bo.umss.app.inventorySp.business.provider.dto.ProviderDto;
import bo.umss.app.inventorySp.exception.EmptyFieldException;
import bo.umss.app.inventorySp.exception.NegativeFieldException;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDto {

	@NotNull
	private String code;

	@NotNull
	private String description;

	@NotNull
	private Integer stock;

	@NotNull
	private MeasurementDto measurement;

	@NotNull
	private Double priceCost;

	@NotNull
	private Double priceSale;

	@NotNull
	private CoinDto coin;

	@NotNull
	private LineDto line;

	@NotNull
	private ProviderDto provider;

	public ProductDto(String code, String description, Integer stock, MeasurementDto measurement, Double priceCost,
			Double priceSale, CoinDto coin, LineDto line, ProviderDto provider) {
		this.code = code;
		this.description = description;
		this.stock = stock;
		this.measurement = measurement;
		this.priceCost = priceCost;
		this.priceSale = priceSale;
		this.coin = coin;
		this.line = line;
		this.provider = provider;
	}

	public static ProductDto at(@NotNull String code, @NotNull String description, @NotNull Integer stock,
			@NotNull MeasurementDto measurement, @NotNull Double priceCost, @NotNull Double priceSale,
			@NotNull CoinDto coin, @NotNull LineDto line, @NotNull ProviderDto provider) {
		if (code.isEmpty())
			throw new EmptyFieldException(Product.CODE_CAN_NOT_BE_BLANK);
		if (description.isEmpty())
			throw new EmptyFieldException(Product.DESCRIPTION_CAN_NOT_BE_BLANK);
		if (0 > stock)
			throw new NegativeFieldException(Product.STOCK_CAN_NOT_BE_LESS_THAN_ZERO);
		if (null == measurement)
			throw new RuntimeException(Product.MEASUREMENT_CAN_NOT_BE_NULL);
		if (null == priceCost)
			throw new RuntimeException(Product.PRICE_COST_CAN_NOT_BE_NULL);
		if (null == priceSale)
			throw new RuntimeException(Product.PRICE_SALE_CAN_NOT_BE_NULL);
		if (null == coin)
			throw new RuntimeException(Product.COIN_CAN_NOT_BE_NULL);
		if (null == line)
			throw new EmptyFieldException(Product.LINE_CAN_NOT_BE_NULL);
		if (null == provider)
			throw new EmptyFieldException(Product.PROVIDER_CAN_NOT_BE_NULL);

		return new ProductDto(code, description, stock, measurement, priceCost, priceSale, coin, line, provider);
	}

	public ProductDto() {
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public Integer getStock() {
		return stock;
	}

	public MeasurementDto getMeasurement() {
		return measurement;
	}

	public Double getPriceCost() {
		return priceCost;
	}

	public Double getPriceSale() {
		return priceSale;
	}

	public CoinDto getCoin() {
		return coin;
	}

	public LineDto getLine() {
		return line;
	}

	public ProviderDto getProvider() {
		return provider;
	}
}
