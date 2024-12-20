package bo.umss.app.inventorySp.business.sale.model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import bo.umss.app.inventorySp.NoteTransaction;
import bo.umss.app.inventorySp.business.price.model.Price;
import bo.umss.app.inventorySp.business.product.model.Product;

public class NoteSale extends NoteTransaction {

	public static final String NAME_CAN_NOT_BE_EMPTY = "Name can not be empty";
	public static final String NIT_CAN_NOT_BE_EMPTY = "Nit can not be empty";
	public static final String INVOICE_NUMBER_CAN_NOT_BE_EMPTY = "Invoice number can not be empty";

	private String name;
	private String nit;
	private String invoiceNumber;

	public NoteSale(LocalDate date, String name, String nit, String invoiceNumber) {
		this.date = date;
		this.name = name;
		this.invoiceNumber = invoiceNumber;
		setProductOutput = new HashSet<>();
	}

	public static NoteSale at(LocalDate date, String name, String nit, String invoiceNumber) {
		if (null == date)
			throw new RuntimeException(NoteTransaction.DATE_CAN_NOT_BE_NULL);
		if (name.isEmpty())
			throw new RuntimeException(NAME_CAN_NOT_BE_EMPTY);
		if (nit.isEmpty())
			throw new RuntimeException(NIT_CAN_NOT_BE_EMPTY);
		if (invoiceNumber.isEmpty())
			throw new RuntimeException(INVOICE_NUMBER_CAN_NOT_BE_EMPTY);

		return new NoteSale(date, name, nit, invoiceNumber);
	}

	@Override
	public LocalDate getDate() {
		return date;
	}

	public String getName() {
		return name;
	}

	public String getNit() {
		return nit;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	@Override
	public Set<Product> getSetProductOutPut() {
		return setProductOutput;
	}

	@Override
	public Boolean compareSizeGreaterZero(Integer potentialSize) {
		return setProductOutput.size() >= potentialSize;
	}

	@Override
	public Map<String, Price> calculateTotal() {
		Map<String, Price> mapResponse = new HashMap<>();
		for (Product productOutput : setProductOutput) {
			Price subtotal = productOutput.generateSubtotal();
			Double sumarizePrice = subtotal.getValue();
			if (0 < mapResponse.size() && null != mapResponse.get(productOutput.getPriceSale().getCoin().getCode())) {
				sumarizePrice = mapResponse.get(productOutput.getPriceSale().getCoin().getCode())
						.addWithOtherPrice(subtotal);
			}
			mapResponse.put(productOutput.getPriceSale().getCoin().getCode(),
					Price.at(sumarizePrice, subtotal.getCoin()));
		}

		return mapResponse;
	}

	@Override
	public Boolean compareIsEqualSize(Integer potentialSize) {
		return setProductOutput.size() == potentialSize;
	}

	@Override
	public Boolean addProduct(Product potentialProduct, Integer potentialAmount) {
		Boolean response = Boolean.FALSE;
		if (potentialProduct.canDecreaseStock(potentialAmount)) {
			potentialProduct.todoDecrementStock(potentialAmount);
			setProductOutput.add(potentialProduct);
			response = Boolean.TRUE;
		}

		return response;
	}
}
