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

import bo.umss.app.inventorySp.business.price.model.Price;
import bo.umss.app.inventorySp.business.stock.model.Stock;

@Entity
@Table(name = "chp_change_price")
public class ChangePrice implements Serializable {

	private static final long serialVersionUID = -8416497435495041457L;

	public static final String NEW_PRICE_CAN_NOT_BE_NULL = "New price can not be null";
	public static final String OLD_PRICE_CAN_NOT_BE_NULL = "Old price can not be null";
	public static final String NEW_PRICE_CAN_NOT_LESS_THAN_OLD_PRICE = "New price can not be less than old price";
	public static final String NEW_PRICE_DOES_NOT_HAS_DIFF_MEASUREMENT_TO_OLD_PRICE = "New Price does not has diff measurement to old price";
	public static final String STOCK_CAN_NOT_BE_NULL = "Stock can not be null";
	public static final String CURRENT_DATE_CAN_NOT_BE_NULL = "Current date can not be null";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@SequenceGenerator(name = "chpr_change_price", sequenceName = "chp_seq", initialValue = 1000)
	@Column(name = "chp_id")
	private Long id;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "chp_pr_id", nullable = false)
	private Price newPrice;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "chp_sc_pr_id", nullable = false)
	private Price oldPrice;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "chp_ms_id", nullable = false)
	private Stock stock;

	@NotNull
	@Column(name = "chp_current_date")
	private LocalDate currentDate;

	public ChangePrice(Price newPrice, Price oldPrice, Stock stock, LocalDate currentDate) {
		this.newPrice = newPrice;
		this.oldPrice = oldPrice;
		this.stock = stock;
		this.currentDate = currentDate;
	}

	public ChangePrice() {
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

	public Long getId() {
		return id;
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
