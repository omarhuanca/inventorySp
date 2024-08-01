package bo.umss.app.inventorySp.business.changePrice.model;

import java.time.LocalDate;

import bo.umss.app.inventorySp.business.price.model.Price;
import bo.umss.app.inventorySp.business.stock.model.Stock;

public class ChangePrice {

	public static final String NEW_PRICE_CAN_NOT_BE_NULL = "New price can not be null";
	public static final String OLD_PRICE_CAN_NOT_BE_NULL = "Old price can not be null";
	public static final String NEW_PRICE_CAN_NOT_LESS_THAN_OLD_PRICE = "New price can not be less than old price";
	public static final String NEW_PRICE_DOES_NOT_HAS_DIFF_MEASUREMENT_TO_OLD_PRICE = "New Price does not has diff measurement to old price";
	public static final String STOCK_CAN_NOT_BE_NULL = "Stock can not be null";
	public static final String CURRENT_DATE_CAN_NOT_BE_NULL = "Current date can not be null";

	private Price newPrice;
	private Price oldPrice;
	private Stock stock;
	private LocalDate currentDate;

	public ChangePrice(Price newPrice, Price oldPrice, Stock stock, LocalDate currentDate) {
		this.newPrice = newPrice;
		this.oldPrice = oldPrice;
		this.stock = stock;
		this.currentDate = currentDate;
	}

	public static ChangePrice at(Price newPrice, Price oldPrice, Stock stock, LocalDate currentDate) {
		if (null == newPrice)
			throw new RuntimeException(NEW_PRICE_CAN_NOT_BE_NULL);
		if (null == oldPrice)
			throw new RuntimeException(OLD_PRICE_CAN_NOT_BE_NULL);
		if (newPrice.lessThanValue(oldPrice))
			throw new RuntimeException(NEW_PRICE_CAN_NOT_LESS_THAN_OLD_PRICE);
		if (!newPrice.getCoin().compareCode(oldPrice.getCoin()))
			throw new RuntimeException(NEW_PRICE_DOES_NOT_HAS_DIFF_MEASUREMENT_TO_OLD_PRICE);
		if (null == stock)
			throw new RuntimeException(STOCK_CAN_NOT_BE_NULL);
		if (null == currentDate)
			throw new RuntimeException(CURRENT_DATE_CAN_NOT_BE_NULL);

		return new ChangePrice(newPrice, oldPrice, stock, currentDate);
	}

	public Price getNewPrice() {
		return newPrice;
	}

	public Price getOldPrice() {
		return oldPrice;
	}

	public Stock getStock() {
		return stock;
	}

	public LocalDate getCurrentDate() {
		return currentDate;
	}
}
