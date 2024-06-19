package bo.umss.app.inventorySp.codeProduct.model;

import bo.umss.app.inventorySp.codeProduct.CodeProduct;
import bo.umss.app.inventorySp.line.model.Line;
import bo.umss.app.inventorySp.provider.model.Provider;

public class NotProvidedProvider extends CodeProduct {

	public String DEFAULT_PROVIDER_NAME = "Pepe";
	public String DEFAULT_PROVIDER_PHONE_NUMBER = "71476576";

	public NotProvidedProvider(String code, String description, Line line) {
		this.code = code;
		this.description = description;
		this.line = line;
	}

	public static NotProvidedProvider at(String code, String description, Line line) {
		if (code.isEmpty())
			throw new RuntimeException(CODE_CAN_NOT_BE_BLANK);
		if (description.isEmpty())
			throw new RuntimeException(DESCRIPTION_CAN_NOT_BE_BLANK);
		if (null == line)
			throw new RuntimeException(LINE_CAN_NOT_BE_NULL);

		return new NotProvidedProvider(code, description, line);
	}

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public Line getLine() {
		return line;
	}

	@Override
	public Boolean compareAnotherCode(CodeProduct otherCodeProduct) {
		return code.equalsIgnoreCase(otherCodeProduct.getCode());
	}

	@Override
	public Provider getProvider() {
		return new Provider(DEFAULT_PROVIDER_NAME, DEFAULT_PROVIDER_PHONE_NUMBER);
	}
}
