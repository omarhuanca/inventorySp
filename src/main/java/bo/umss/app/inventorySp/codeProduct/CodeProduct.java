package bo.umss.app.inventorySp.codeProduct;

import bo.umss.app.inventorySp.line.model.Line;
import bo.umss.app.inventorySp.provider.model.Provider;

public abstract class CodeProduct {

	public static final String CODE_CAN_NOT_BE_BLANK = "Code can not be blank";
	public static final String DESCRIPTION_CAN_NOT_BE_BLANK = "Description can not be blank";
	public static final String LINE_CAN_NOT_BE_NULL = "Line can not be null";
	public static final String COIN_CAN_NOT_BE_NULL = "Coin can not be null";

	protected String code;
	protected String description;
	protected Line line;

	public abstract String getCode();

	public abstract String getDescription();

	public abstract Line getLine();

	public abstract Boolean compareAnotherCode(CodeProduct otherCodeProduct);

	public abstract Provider getProvider();

}
