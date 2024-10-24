package bo.umss.app.inventorySp.business.product.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import bo.umss.app.inventorySp.business.line.dto.LineDto;
import bo.umss.app.inventorySp.business.price.dto.PriceDto;
import bo.umss.app.inventorySp.business.product.model.Product;
import bo.umss.app.inventorySp.business.provider.dto.ProviderDto;
import bo.umss.app.inventorySp.business.stock.dto.StockDto;
import bo.umss.app.inventorySp.exception.EmptyFieldException;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDto {

	@NotNull
	private String code;

	@NotNull
	private String description;

	@NotNull
	private StockDto stock;

	@NotNull
	private PriceDto priceCost;

	@NotNull
	private PriceDto priceSale;

	@NotNull
	private LineDto line;

	@NotNull
	private ProviderDto provider;

	public ProductDto(String code, String description, StockDto stock, PriceDto priceCost, PriceDto priceSale,
			LineDto line, ProviderDto provider) {
		this.code = code;
		this.description = description;
		this.stock = stock;
		this.priceCost = priceCost;
		this.priceSale = priceSale;
		this.line = line;
		this.provider = provider;
	}

	public static ProductDto at(@NotNull String code, @NotNull String description, @NotNull StockDto stock,
			@NotNull PriceDto priceCost, @NotNull PriceDto priceSale, @NotNull LineDto line,
			@NotNull ProviderDto provider) {
		if (code.isEmpty())
			throw new EmptyFieldException(Product.CODE_CAN_NOT_BE_BLANK);
		if (description.isEmpty())
			throw new EmptyFieldException(Product.DESCRIPTION_CAN_NOT_BE_BLANK);
		if (null == stock)
			throw new RuntimeException(Product.STOCK_CAN_NOT_BE_NULL);
		if (null == priceCost)
			throw new RuntimeException(Product.PRICE_COST_CAN_NOT_BE_NULL);
		if (null == priceSale)
			throw new RuntimeException(Product.PRICE_SALE_CAN_NOT_BE_NULL);
		if (null == line)
			throw new EmptyFieldException(Product.LINE_CAN_NOT_BE_NULL);

		return new ProductDto(code, description, stock, priceCost, priceSale, line, provider);
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public StockDto getStock() {
		return stock;
	}

	public PriceDto getPriceCost() {
		return priceCost;
	}

	public PriceDto getPriceSale() {
		return priceSale;
	}

	public LineDto getLine() {
		return line;
	}

	public ProviderDto getProvider() {
		return provider;
	}
}
