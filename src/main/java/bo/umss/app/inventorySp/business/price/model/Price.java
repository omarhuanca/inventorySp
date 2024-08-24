package bo.umss.app.inventorySp.business.price.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import bo.umss.app.inventorySp.business.coin.model.Coin;
import bo.umss.app.inventorySp.business.discount.model.Discount;
import bo.umss.app.inventorySp.exception.EmptyFieldException;
import bo.umss.app.inventorySp.exception.NegativeFieldException;

@Entity
@Table(name = "pr_price", uniqueConstraints = { @UniqueConstraint(columnNames = { "pr_code" }) })
public class Price implements Serializable {

	private static final long serialVersionUID = 4936765486395174270L;

	public static final String CODE_CAN_NOT_BE_BLANK = "Code can not be blank";
	public static final String VALUE_CAN_NOT_BE_LESS_ZERO = "Value can not be less than zero";
	public static final String COIN_CAN_NOT_BE_NULL = "Coin can not be null";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@SequenceGenerator(name = "prc_price", sequenceName = "pr_seq", initialValue = 1000)
	@Column(name = "pr_id")
	private Long id;

	@NotNull
	@Column(name = "pr_code")
	private String code;

	@NotNull
	@Column(name = "pr_value")
	private Double value;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "pr_cn_id", nullable = false)
	private Coin coin;

	public Price(String code, double value, Coin coin) {
		this.code = code;
		this.value = value;
		this.coin = coin;
	}

	public Price() {
	}

	public static Price at(String code, Double value, Coin coin) {
		if (code.isEmpty())
			throw new EmptyFieldException(CODE_CAN_NOT_BE_BLANK);
		if (0 >= value)
			throw new NegativeFieldException(VALUE_CAN_NOT_BE_LESS_ZERO);
		if (null == coin)
			throw new RuntimeException(COIN_CAN_NOT_BE_NULL);

		return new Price(code, value, coin);
	}

	public String getCode() {
		return code;
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
			result.put(coin, Price.at(code, value + potentialPrice.getValue(), coin));
		} else {
			result.put(potentialPrice.getCoin(), potentialPrice);
		}

		return result;
	}

	public Boolean compareOtherValue(Double potentialValue) {
		return value.equals(potentialValue);
	}

	public Double addWithOtherPrice(Price potentialPrice) {
		return value + potentialPrice.getValue();
	}

	public Price applyDiscount(Discount discount) {
		Price response = Price.at(code, value, coin);
		if (value > discount.getValue()) {
			response = Price.at(code, value - discount.getValue(), coin);
		}

		return response;
	}

	public void setValue(Double potentialValue) {
		value = potentialValue;
	}

	public Boolean isNegativeValue() {
		return 0 > value;
	}

	public Boolean compareAnotherCode(String potentialCode) {
		return code.equalsIgnoreCase(potentialCode);
	}

	public void setCoin(Coin potentialCoin) {
		coin = potentialCoin;
	}
}
