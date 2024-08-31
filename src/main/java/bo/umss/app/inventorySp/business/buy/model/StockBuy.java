package bo.umss.app.inventorySp.business.buy.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

import bo.umss.app.inventorySp.StockTransaction;
import bo.umss.app.inventorySp.business.product.model.Product;

public class StockBuy extends StockTransaction implements Serializable {

	private static final long serialVersionUID = -6649984244653175299L;

	public static final String DESCRIPTION_CAN_NOT_BE_BLANK = "Description can not be empty";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@SequenceGenerator(name = "stck_stock_buy", sequenceName = "stc_seq", initialValue = 1000)
	@Column(name = "stc_id")
	private Long id;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "stc_prd_id", nullable = false)
	private Product product;

	@NotNull
	@Column(name = "stc_amount")
	private Integer amount;

	@NotNull
	@Column(name = "stc_local_date")
	private LocalDate localDate;

	@NotNull
	@Column(name = "stc_description")
	private String description;

	public StockBuy(Product product, Integer amount, LocalDate localDate, String description) {
		this.product = product;
		this.amount = amount;
		this.localDate = localDate;
		this.description = description;
	}

	public static StockBuy at(Product product, Integer amount, LocalDate localDate, String description) {
		if (null == product)
			throw new RuntimeException(StockTransaction.CODE_PRODUCT_CAN_NOT_BE_NULL);
		if (0 >= amount)
			throw new RuntimeException(StockTransaction.AMOUNT_CAN_NOT_BE_LESS_THAN_ZERO);
		if (null == localDate)
			throw new RuntimeException(StockTransaction.DATE_CAN_NOT_BE_NULL);
		if (description.isEmpty())
			throw new RuntimeException(StockBuy.DESCRIPTION_CAN_NOT_BE_BLANK);

		return new StockBuy(product, amount, localDate, description);
	}

	public Long getId() {
		return id;
	}

	@Override
	public Product getProduct() {
		return product;
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

	public Boolean compareAnotherId(Long potentialKey) {
		return id.equals(potentialKey);
	}

	public void setId(Long potentialId) {
		id = potentialId;
	}
}
