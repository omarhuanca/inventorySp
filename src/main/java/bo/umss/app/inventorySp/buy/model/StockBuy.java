package bo.umss.app.inventorySp.buy.model;

import java.time.LocalDate;

import bo.umss.app.inventorySp.StockTransaction;
import bo.umss.app.inventorySp.codeProduct.CodeProduct;

public class StockBuy extends StockTransaction {

	public static final String DESCRIPTION_CAN_NOT_BE_BLANK = "Description can not be empty";

	private String description;

	public StockBuy(CodeProduct codeProduct, Integer amount, LocalDate localDate, String description) {
		this.codeProduct = codeProduct;
		this.amount = amount;
		this.localDate = localDate;
		this.description = description;
	}

	public static StockBuy at(CodeProduct codeProduct, Integer amount, LocalDate localDate, String description) {
		if (null == codeProduct)
			throw new RuntimeException(StockTransaction.CODE_PRODUCT_CAN_NOT_BE_NULL);
		if (0 >= amount)
			throw new RuntimeException(StockTransaction.AMOUNT_CAN_NOT_BE_LESS_THAN_ZERO);
		if (null == localDate)
			throw new RuntimeException(StockTransaction.DATE_CAN_NOT_BE_NULL);
		if (description.isEmpty())
			throw new RuntimeException(StockBuy.DESCRIPTION_CAN_NOT_BE_BLANK);

		return new StockBuy(codeProduct, amount, localDate, description);
	}

	@Override
	public CodeProduct getCodeProduct() {
		return codeProduct;
	}

	@Override
	public Integer getAmount() {
		return amount;
	}

	@Override
	public LocalDate getLocalDate() {
		return localDate;
	}

	public String getDescription() {
		return description;
	}
}
