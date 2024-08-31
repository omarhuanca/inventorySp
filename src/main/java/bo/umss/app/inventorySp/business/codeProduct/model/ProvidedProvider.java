package bo.umss.app.inventorySp.business.codeProduct.model;

import bo.umss.app.inventorySp.business.line.model.Line;
import bo.umss.app.inventorySp.business.provider.model.Provider;

public class ProvidedProvider extends CodeProduct {

	public static final String PROVIDER_CAN_NOT_BE_NULL = "Provider can not be null.";
	private Provider provider;

	private String code;

	private String description;

	private Line line;

	public ProvidedProvider(String code, String description, Line line, Provider provider) {
		this.code = code;
		this.description = description;
		this.line = line;
		this.provider = provider;
	}

	public static ProvidedProvider at(String code, String description, Line line, Provider provider) {
		if (code.isEmpty())
			throw new RuntimeException(CODE_CAN_NOT_BE_BLANK);
		if (description.isEmpty())
			throw new RuntimeException(DESCRIPTION_CAN_NOT_BE_BLANK);
		if (null == line)
			throw new RuntimeException(LINE_CAN_NOT_BE_NULL);
		if (null == provider)
			throw new RuntimeException(PROVIDER_CAN_NOT_BE_NULL);

		return new ProvidedProvider(code, description, line, provider);
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

	public Provider getProvider() {
		return provider;
	}
}