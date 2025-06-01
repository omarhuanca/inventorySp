package bo.umss.app.inventorySp.business.referral.model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import bo.umss.app.inventorySp.NoteTransaction;
import bo.umss.app.inventorySp.business.product.model.Product;

public class NoteReferral extends NoteTransaction {

	public NoteReferral(LocalDate date) {
		this.date = date;
		setProductOutput = new HashSet<>();
	}

	public static NoteReferral at(LocalDate date) {
		if (null == date)
			throw new RuntimeException(NoteTransaction.DATE_CAN_NOT_BE_NULL);

		return new NoteReferral(date);
	}

	@Override
	public LocalDate getDate() {
		return date;
	}

	@Override
	public Set<Product> getSetProductOutPut() {
		return setProductOutput;
	}

	@Override
	public Boolean compareIsEqualSize(Integer potentialSize) {
		return setProductOutput.size() == potentialSize;
	}

	@Override
	public Boolean compareSizeGreaterZero(Integer potentialSize) {
		return setProductOutput.size() > potentialSize;
	}

	@Override
	public Map<String, Double> calculateTotal() {
		Map<String, Double> mapResponse = new HashMap<>();
		for (Product productOutput : setProductOutput) {
			Double subtotal = productOutput.generateSubtotal();
			Double sumarizePrice = Double.valueOf(subtotal);
			if (0 < mapResponse.size() && null != mapResponse.get(productOutput.getCoin().getCode())) {
				sumarizePrice = mapResponse.get(productOutput.getCoin().getCode()) + subtotal;
			}
			mapResponse.put(productOutput.getCoin().getCode(), sumarizePrice);
		}

		return mapResponse;
	}

	@Override
	public Boolean addProduct(Product potentialProduct, Integer potentialAmount) {
		Boolean response = Boolean.FALSE;
		if (potentialProduct.canDecreaseStock(potentialAmount)) {
			potentialProduct.todoDecrementStock(potentialAmount);
			setProductOutput.add(potentialProduct);
		}

		return response;
	}
}
