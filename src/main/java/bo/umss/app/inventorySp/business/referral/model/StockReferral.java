package bo.umss.app.inventorySp.business.referral.model;

import java.time.LocalDate;

import bo.umss.app.inventorySp.StockTransaction;
import bo.umss.app.inventorySp.business.codeProduct.CodeProduct;

public class StockReferral extends StockTransaction {

	public StockReferral(CodeProduct codeProduct, Integer amount, LocalDate localDate) {
		this.codeProduct = codeProduct;
		this.amount = amount;
		this.localDate = localDate;
	}

	public static StockReferral at(CodeProduct codeProduct, Integer amount, LocalDate localDate) {
		if (null == codeProduct)
			throw new RuntimeException(StockTransaction.CODE_PRODUCT_CAN_NOT_BE_NULL);
		if (0 >= amount)
			throw new RuntimeException(StockTransaction.AMOUNT_CAN_NOT_BE_LESS_THAN_ZERO);
		if(null == localDate)
			throw new RuntimeException(StockTransaction.DATE_CAN_NOT_BE_NULL);

		return new StockReferral(codeProduct, amount, localDate);
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
}
