package bo.umss.app.inventorySp.business.payment.model;

import bo.umss.app.inventorySp.NoteTransaction;
import bo.umss.app.inventorySp.business.discount.model.Discount;
import bo.umss.app.inventorySp.business.price.model.Price;

public class PaymentTransaction {

	public static final String TOTAL_CAN_NOT_BE_NULL = "Total can not be null";
	public static final String DISCOUNT_CAN_NOT_BE_NULL = "Discount can not be null";
	public static final String NOTE_TRANSACTION_CAN_NOT_BE_NULL = "Note referral can not be null";
	public static final String PRODUCT_SIZE_GREATER_THAN = "List product is greater than";
	public static final String TOTAL_VALUE_LESS_THAN_DISCOUNT_VALUE = "Total value is less than discount value";
	public static final String AMOUNT_TO_PAY_CAN_NOT_BE_NEGATIVE = "Amont to pay can not be negative";

	private Price total;
	private Discount discount;
	private NoteTransaction noteTransaction;

	public PaymentTransaction(Price total, Discount discount, NoteTransaction noteTransaction) {
		this.total = total;
		this.discount = discount;
		this.noteTransaction = noteTransaction;
	}

	public static PaymentTransaction at(Price total, Discount discount, NoteTransaction noteTransaction) {
		if (null == total)
			throw new RuntimeException(TOTAL_CAN_NOT_BE_NULL);
		if (null == discount)
			throw new RuntimeException(DISCOUNT_CAN_NOT_BE_NULL);
		if (null == noteTransaction)
			throw new RuntimeException(NOTE_TRANSACTION_CAN_NOT_BE_NULL);
		if (!noteTransaction.compareSizeGreaterZero(0))
			throw new RuntimeException(PRODUCT_SIZE_GREATER_THAN);
		if(total.compareValueLessThanPotentialValue(discount.getValue()))
			throw new RuntimeException(TOTAL_VALUE_LESS_THAN_DISCOUNT_VALUE);

		return new PaymentTransaction(total, discount, noteTransaction);
	}

	public Price getTotal() {
		return total;
	}

	public Discount getDiscount() {
		return discount;
	}

	public NoteTransaction getNoteTransaction() {
		return noteTransaction;
	}

	public Price generateAmountToPay() {
		return total.applyDiscount(discount);
	}
}
