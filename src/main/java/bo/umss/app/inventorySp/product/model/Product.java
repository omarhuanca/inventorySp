package bo.umss.app.inventorySp.product.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import bo.umss.app.inventorySp.StockTransaction;
import bo.umss.app.inventorySp.buy.model.StockBuy;
import bo.umss.app.inventorySp.changePrice.model.ChangePrice;
import bo.umss.app.inventorySp.codeProduct.CodeProduct;
import bo.umss.app.inventorySp.price.model.Price;
import bo.umss.app.inventorySp.referral.model.StockReferral;
import bo.umss.app.inventorySp.stock.model.Stock;

public class Product {

	public static final String CODE_CAN_NOT_BE_NULL = "Code product can not be null";
	public static final String DESCRIPTION_CAN_NOT_BE_NULL = "Description can not be null";
	public static final String STOCK_CAN_NOT_BE_NULL = "Stock can not be null";
	public static final String CODE_PRODUCT_DUPLICATE = "Code product already exists";
	public static final String PRICE_COST_CAN_NOT_BE_NULL = "Price cost can not be null";
	public static final String PRICE_SALE_CAN_NOT_BE_NULL = "Price sale can not be null";

	private CodeProduct codeProduct;
	private Stock stock;
	private Price priceCost;
	private Price priceSale;
	private List<ChangePrice> listChangePriceCost;
	private List<StockTransaction> listTransaction;

	public Product(CodeProduct codeProduct, Stock stock, Price priceCost, Price priceSale) {
		this.codeProduct = codeProduct;
		this.stock = stock;
		this.priceCost = priceCost;
		this.priceSale = priceSale;
		this.listChangePriceCost = new ArrayList<>();
		this.listTransaction = new ArrayList<>();
	}

	public static Product at(CodeProduct codeProduct, Stock stock, Price priceCost, Price priceSale) {
		if (null == codeProduct)
			throw new RuntimeException(CODE_CAN_NOT_BE_NULL);
		if (null == stock)
			throw new RuntimeException(STOCK_CAN_NOT_BE_NULL);
		if (null == priceCost)
			throw new RuntimeException(PRICE_COST_CAN_NOT_BE_NULL);
		if (null == priceSale)
			throw new RuntimeException(PRICE_SALE_CAN_NOT_BE_NULL);

		return new Product(codeProduct, stock, priceCost, priceSale);
	}

	public CodeProduct getCodeProduct() {
		return codeProduct;
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

	public List<ChangePrice> getListChangePriceCost() {
		return listChangePriceCost;
	}

	public List<StockTransaction> getListTransaction() {
		return listTransaction;
	}

	@Override
	public boolean equals(Object obj) {
		Product potentialProduct = (Product) obj;
		return codeProduct.compareAnotherCode(potentialProduct.getCodeProduct());
	}

	public Boolean listTransactionCompareGreatherThanZero(Integer count) {
		return listTransaction.size() > count;
	}

	public Boolean alreadyCodeProduct() {
		return null != codeProduct;
	}

	public void addBuy(StockBuy buy) {
		if (stock.amountGreaterThanZero()) {

			if (stock.verifyPotentialValueGreaterZero(buy.getAmount())) {
				stock.todoIncreaseStock(buy.getAmount());
			}
			listTransaction.add(buy);
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
		if (stock.compareValue(potentialStock.getValue())) {
			setStock(potentialStock);
		}
	}

	public void addReferral(StockReferral referral) {
		todoDecrementStock(referral.getAmount());
		listTransaction.add(referral);
	}

	public Price calculateSubtotalWithCoin() {
		return Price.at(priceSale.getValue() * stock.getValue(), priceSale.getCoin());
	}

	public Price generateSubtotal() {
		return Price.at(priceSale.getValue() * stock.getValue(), priceSale.getCoin());
	}
}
