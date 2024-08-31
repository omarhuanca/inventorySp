package bo.umss.app.inventorySp.business.product.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import bo.umss.app.inventorySp.business.buy.model.StockBuy;
import bo.umss.app.inventorySp.business.changePrice.model.ChangePrice;
import bo.umss.app.inventorySp.business.line.model.Line;
import bo.umss.app.inventorySp.business.price.model.Price;
import bo.umss.app.inventorySp.business.provider.model.Provider;
import bo.umss.app.inventorySp.business.referral.model.StockReferral;
import bo.umss.app.inventorySp.business.stock.model.Stock;
import bo.umss.app.inventorySp.exception.EmptyFieldException;

public class Product implements Serializable {

	private static final long serialVersionUID = -3585952891558067223L;

	public static final String CODE_CAN_NOT_BE_BLANK = "Code can not be blank";
	public static final String DESCRIPTION_CAN_NOT_BE_BLANK = "Description can not be blank";
	public static final String STOCK_CAN_NOT_BE_NULL = "Stock can not be null";
	public static final String CODE_PRODUCT_DUPLICATE = "Code product already exists";
	public static final String PRICE_COST_CAN_NOT_BE_NULL = "Price cost can not be null";
	public static final String PRICE_SALE_CAN_NOT_BE_NULL = "Price sale can not be null";
	public static final String LINE_CAN_NOT_BE_NULL = "Line can not be null";

	private String code;

	private String description;

	private Stock stock;

	private Price priceCost;

	private Price priceSale;

	private Line line;

	private Provider provider;
	private List<ChangePrice> listChangePriceCost;
	private List<StockBuy> listStockBuy;
	private List<StockReferral> listReferral;

	public Product(String code, String description, Stock stock, Price priceCost, Price priceSale, Line line,
			Provider provider) {
		this.code = code;
		this.description = description;
		this.stock = stock;
		this.priceCost = priceCost;
		this.priceSale = priceSale;
		this.line = line;
		this.provider = provider;
		listChangePriceCost = new ArrayList<>();
		listStockBuy = new ArrayList<>();
		listReferral = new ArrayList<>();
	}

	public static Product at(String code, String description, Stock stock, Price priceCost, Price priceSale, Line line,
			Provider provider) {
		if (code.isEmpty())
			throw new EmptyFieldException(CODE_CAN_NOT_BE_BLANK);
		if (description.isEmpty())
			throw new EmptyFieldException(DESCRIPTION_CAN_NOT_BE_BLANK);
		if (null == stock)
			throw new RuntimeException(STOCK_CAN_NOT_BE_NULL);
		if (null == priceCost)
			throw new RuntimeException(PRICE_COST_CAN_NOT_BE_NULL);
		if (null == priceSale)
			throw new RuntimeException(PRICE_SALE_CAN_NOT_BE_NULL);
		if (null == line)
			throw new EmptyFieldException(LINE_CAN_NOT_BE_NULL);

		return new Product(code, description, stock, priceCost, priceSale, line, provider);
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public Stock getStock() {
		return stock;
	}

	private void setStock(Stock potentialStock) {
		stock = potentialStock;
	}

	public Price getPriceCost() {
		return priceCost;
	}

	public void setPriceCost(Price potentialPriceCost) {
		priceCost = potentialPriceCost;
	}

	public Price getPriceSale() {
		return priceSale;
	}

	public Line getLine() {
		return line;
	}

	public Provider getProvider() {
		return provider;
	}

	public List<ChangePrice> getListChangePriceCost() {
		return listChangePriceCost;
	}

	public List<StockBuy> getListStockBuy() {
		return listStockBuy;
	}

	public List<StockReferral> getListReferral() {
		return listReferral;
	}

	public void setListReferral(List<StockReferral> listReferral) {
		this.listReferral = listReferral;
	}

	public boolean equals(Product potentialProduct) {
		return code.equalsIgnoreCase(potentialProduct.getCode());
	}

	public Boolean listTransactionCompareGreatherThanZero(Integer count) {
		return listStockBuy.size() > count;
	}

	public void addBuy(StockBuy buy) {
		if (stock.amountGreaterThanZero()) {

			if (stock.verifyPotentialValueGreaterZero(buy.getAmount())) {
				stock.todoIncreaseStock(buy.getAmount());
			}
			listStockBuy.add(buy);
		}
	}

	public Boolean lessThanValuePriceCost(Price potentialPriceCost) {
		return priceCost.lessThanValue(potentialPriceCost);
	}

	public void changePriceBuy(Price potentialPriceCost, Stock stock) {
		if (lessThanValuePriceCost(potentialPriceCost)
				&& priceCost.getCoin().compareCode(potentialPriceCost.getCoin())) {
			LocalDate currentDate = LocalDate.now();
			ChangePrice changePrice = ChangePrice.at(potentialPriceCost, getPriceCost(), stock, currentDate);
			getListChangePriceCost().add(changePrice);
			setPriceCost(potentialPriceCost);
		}

	}

	public Boolean canDecreaseStock(Integer amount) {
		return stock.verifyValueGreaterThanPotentialValue(amount);
	}

	public void todoDecrementStock(Integer amount) {
		stock.todoDecrementStock(amount);
	}

	public void changeMesurementStock(Stock potentialStock) {
		if (stock.compareOtherValue(potentialStock.getValue())) {
			setStock(potentialStock);
		}
	}

	public void addReferral(StockReferral referral) {
		todoDecrementStock(referral.getAmount());
		listReferral.add(referral);
	}

	public Price calculateSubtotalWithCoin() {
		return Price.at("PR-1", priceSale.getValue() * stock.getValue(), priceSale.getCoin());
	}

	public Price generateSubtotal() {
		return Price.at("PR-1", priceSale.getValue() * stock.getValue(), priceSale.getCoin());
	}
}
