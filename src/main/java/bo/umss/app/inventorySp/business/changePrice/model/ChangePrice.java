package bo.umss.app.inventorySp.business.changePrice.model;

import java.io.Serializable;
import java.time.LocalDate;

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
import javax.validation.constraints.NotNull;

import bo.umss.app.inventorySp.business.coin.model.Coin;
import bo.umss.app.inventorySp.business.measurement.model.Measurement;
import bo.umss.app.inventorySp.exception.ValueLessThanOtherException;

@Entity
@Table(name = "chp_change_price")
public class ChangePrice implements Serializable {

	private static final long serialVersionUID = -8416497435495041457L;

	public static final String NEW_PRICE_CAN_NOT_BE_NULL = "New price can not be null";
	public static final String OLD_PRICE_CAN_NOT_BE_NULL = "Old price can not be null";
	public static final String NEW_PRICE_CAN_NOT_LESS_THAN_OLD_PRICE = "New price can not be less than old price";
	public static final String NEW_PRICE_DOES_NOT_HAS_DIFF_MEASUREMENT_TO_OLD_PRICE = "New Price does not has diff measurement to old price";
	public static final String STOCK_CAN_NOT_BE_NULL = "Stock can not be null";
	public static final String MEASUREMENT_CAN_NOT_BE_NULL = "Measurement can not be null";
	public static final String CURRENT_DATE_CAN_NOT_BE_NULL = "Current date can not be null";
	public static final String COIN_CAN_NOT_BE_NULL = "Coin can not be null";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@SequenceGenerator(name = "chpr_change_price", sequenceName = "chp_seq", initialValue = 1000)
	@Column(name = "chp_id")
	private Long id;

	@NotNull
	@Column(name = "chp_new_price")
	private Double newPrice;

	@NotNull
	@Column(name = "chp_old_price")
	private Double oldPrice;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "chp_cn_id", nullable = false)
	private Coin coin;

	@NotNull
	@Column(name = "chp_stock")
	private Integer stock;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "chp_ms_id", nullable = false)
	private Measurement measurement;

	@NotNull
	@Column(name = "chp_current_date")
	private LocalDate currentDate;

	public ChangePrice(Double newPrice, Double oldPrice, Coin coin, Integer stock, Measurement measurement, LocalDate currentDate) {
		this.newPrice = newPrice;
		this.oldPrice = oldPrice;
		this.stock = stock;
		this.measurement = measurement;
		this.currentDate = currentDate;
	}

	public ChangePrice() {
	}

	public static ChangePrice at(Double newPrice, Double oldPrice, Coin coin, Integer stock,
			Measurement measurement, LocalDate currentDate) {
		if (null == newPrice)
			throw new RuntimeException(NEW_PRICE_CAN_NOT_BE_NULL);
		if (null == oldPrice)
			throw new RuntimeException(OLD_PRICE_CAN_NOT_BE_NULL);
		if (newPrice < oldPrice)
			throw new ValueLessThanOtherException(NEW_PRICE_CAN_NOT_LESS_THAN_OLD_PRICE);
		if (null == coin)
			throw new RuntimeException(COIN_CAN_NOT_BE_NULL);
		if (null == stock)
			throw new RuntimeException(STOCK_CAN_NOT_BE_NULL);
		if (null == measurement)
			throw new RuntimeException(MEASUREMENT_CAN_NOT_BE_NULL);
		if (null == currentDate)
			throw new RuntimeException(CURRENT_DATE_CAN_NOT_BE_NULL);

		return new ChangePrice(newPrice, oldPrice, coin, stock, measurement, currentDate);
	}

	public Long getId() {
		return id;
	}

	public Double getNewPrice() {
		return newPrice;
	}

	public Double getOldPrice() {
		return oldPrice;
	}

	public Integer getStock() {
		return stock;
	}

	public Measurement getMeasurement() {
		return measurement;
	}

	public LocalDate getCurrentDate() {
		return currentDate;
	}

	public Boolean lessThanValue(Integer potentialPrice) {
		return newPrice < potentialPrice;
	}
}
