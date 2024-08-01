package bo.umss.app.inventorySp.business.price.model;

import java.util.HashMap;
import java.util.Map;

import bo.umss.app.inventorySp.business.coin.model.Coin;
import bo.umss.app.inventorySp.business.discount.model.Discount;

public class Price {

	public static final String VALUE_CAN_NOT_BE_LESS_ZERO = "Value can not be less than zero";
	public static final String COIN_CAN_NOT_BE_NULL = "Coin can not be null";

	private Double value;
	private Coin coin;

	public Price(double value, Coin coin) {
		this.value = value;
		this.coin = coin;
	}

	public static Price at(Double value, Coin coin) {
		if (value <= 0)
			throw new RuntimeException(VALUE_CAN_NOT_BE_LESS_ZERO);
		if (null == coin)
			throw new RuntimeException(COIN_CAN_NOT_BE_NULL);

		return new Price(value, coin);
	}

	public Double getValue() {
		return value;
	}

	public Coin getCoin() {
		return coin;
	}

	public Boolean lessThanValue(Price potentialPriceCost) {
		return value < potentialPriceCost.getValue();
	}

	public Boolean compareValueLessThanPotentialValue(Integer potentialValue) {
		return value < potentialValue;
	}

	public Map<Coin, Price> addPriceSumarize(Price potentialPrice) {
		Map<Coin, Price> result = new HashMap<>();
		if (coin.compareCode(potentialPrice.getCoin())) {
			result.put(coin, Price.at(value + potentialPrice.getValue(), coin));
		} else {
			result.put(potentialPrice.getCoin(), potentialPrice);
		}

		return result;
	}

	public Boolean compareValue(Double potentialValue) {
		return value.equals(potentialValue);
	}

	public Double addWithOtherPrice(Price potentialPrice) {
		return value + potentialPrice.getValue();
	}

	public Price applyDiscount(Discount discount) {
		Price response = Price.at(value, coin);
		if (value > discount.getValue()) {
			response = Price.at(value - discount.getValue(), coin);
		}

		return response;
	}

}
