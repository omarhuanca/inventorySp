package bo.umss.app.inventorySp;

import java.time.LocalDate;

import bo.umss.app.inventorySp.business.product.model.Product;

public abstract class StockTransaction {

	public static final String PRODUCT_CAN_NOT_BE_NULL = "Product cant not be null";
	public static final String AMOUNT_CAN_NOT_BE_LESS_THAN_ZERO = "Amount can not be greather than zero or equal to zero";
	public static final String DATE_CAN_NOT_BE_NULL = "Date can not be null";

	public abstract Product getProduct();

	public abstract Integer getAmount();

	public abstract LocalDate getLocalDate();
}
