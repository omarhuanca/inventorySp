package bo.umss.app.inventorySp;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

import bo.umss.app.inventorySp.business.price.model.Price;
import bo.umss.app.inventorySp.business.product.model.Product;

public abstract class NoteTransaction {

	public static final String DATE_CAN_NOT_BE_NULL = "Date can not be null";

	protected LocalDate date;
	protected Set<Product> setProductOutput;

	public abstract LocalDate getDate();

	public abstract Set<Product> getSetProductOutPut();

	public abstract Boolean addProduct(Product potentialProduct, Integer potentialAmount);

	public abstract Boolean compareSizeGreaterZero(Integer potentialSize);

	public abstract Map<String, Price> calculateTotal();

	public abstract Boolean compareIsEqualSize(Integer potentialSize);
}
