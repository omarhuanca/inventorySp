package bo.umss.app.inventorySp.business.referral.model;

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

public class StockReferral extends StockTransaction implements Serializable {

	private static final long serialVersionUID = -6985530044746027757L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@SequenceGenerator(name = "stcrf_stock_referral", sequenceName = "stcr_seq", initialValue = 1000)
	@Column(name = "stcr_id")
	private Long id;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "stcr_prd_id", nullable = false)
	private Product product;

	@NotNull
	@Column(name = "stcr_amount")
	private Integer amount;

	@NotNull
	@Column(name = "stcr_local_date")
	private LocalDate localDate;

	public StockReferral(Product product, Integer amount, LocalDate localDate) {
		this.product = product;
		this.amount = amount;
		this.localDate = localDate;
	}

	public static StockReferral at(Product product, Integer amount, LocalDate localDate) {
		if (null == product)
			throw new RuntimeException(StockTransaction.PRODUCT_CAN_NOT_BE_NULL);
		if (0 >= amount)
			throw new RuntimeException(StockTransaction.AMOUNT_CAN_NOT_BE_LESS_THAN_ZERO);
		if (null == localDate)
			throw new RuntimeException(StockTransaction.DATE_CAN_NOT_BE_NULL);

		return new StockReferral(product, amount, localDate);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long potentialId) {
		id = potentialId;
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

	public Boolean compareAnotherKey(Long potentialKey) {
		return id.equals(potentialKey);
	}
}
