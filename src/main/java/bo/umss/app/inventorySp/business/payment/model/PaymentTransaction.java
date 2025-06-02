package bo.umss.app.inventorySp.business.payment.model;

import bo.umss.app.inventorySp.NoteTransaction;
import bo.umss.app.inventorySp.business.coin.model.Coin;

public class PaymentTransaction {

	public static final String TOTAL_CAN_NOT_BE_NULL = "Total can not be null";
	public static final String DISCOUNT_CAN_NOT_BE_NULL = "Discount can not be null";
	public static final String COIN_CAN_NOT_BE_NULL = "Coin can not be null";
	public static final String NOTE_TRANSACTION_CAN_NOT_BE_NULL = "Note referral can not be null";
	public static final String PRODUCT_SIZE_GREATER_THAN = "List product is greater than";
	public static final String TOTAL_VALUE_LESS_THAN_DISCOUNT_VALUE = "Total value is less than discount value";
	public static final String AMOUNT_TO_PAY_CAN_NOT_BE_NEGATIVE = "Amont to pay can not be negative";

	private Double total;
	private Integer discount;
	private Coin coin;
	private NoteTransaction noteTransaction;

	public PaymentTransaction(Double total, Integer discount, Coin coin, NoteTransaction noteTransaction) {
		this.total = total;
		this.discount = discount;
		this.coin = coin;
		this.noteTransaction = noteTransaction;
	}

	public static PaymentTransaction at(Double total, Integer discount, Coin coin, NoteTransaction noteTransaction) {
		if (null == total)
			throw new RuntimeException(TOTAL_CAN_NOT_BE_NULL);
		if (null == discount)
			throw new RuntimeException(DISCOUNT_CAN_NOT_BE_NULL);
		if (null == coin)
			throw new RuntimeException(COIN_CAN_NOT_BE_NULL);
		if (null == noteTransaction)
			throw new RuntimeException(NOTE_TRANSACTION_CAN_NOT_BE_NULL);
		if (!noteTransaction.compareSizeGreaterZero(0))
			throw new RuntimeException(PRODUCT_SIZE_GREATER_THAN);
		if(total < discount)
			throw new RuntimeException(TOTAL_VALUE_LESS_THAN_DISCOUNT_VALUE);

		return new PaymentTransaction(total, discount, coin, noteTransaction);
	}

	public Double getTotal() {
		return total;
	}

	public Integer getDiscount() {
		return discount;
	}

	public Coin getCoin() {
		return coin;
	}

	public NoteTransaction getNoteTransaction() {
		return noteTransaction;
	}

	public Double generateAmountToPay() {
		return applyDiscount();
	}

	public Boolean compareValueLessThanPotentialValue() {
		return total < discount;
	}

	public Double applyDiscount() {
		Double response = total;
		if (total > discount) {
			response = total - discount;
		}

		return response;
	}
}
