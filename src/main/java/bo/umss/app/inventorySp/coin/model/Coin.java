package bo.umss.app.inventorySp.coin.model;

public class Coin {

	public static final String CODE_CAN_NOT_BE_EMPTY = "Code can not be blank";

	private String code;

	private Coin(String code) {
		this.code = code;
	}

	public static Coin at(String code) {
		if (code.isEmpty())
			throw new RuntimeException(CODE_CAN_NOT_BE_EMPTY);

		return new Coin(code);
	}

	public String getCode() {
		return code;
	}

	public Boolean compareCode(Coin potentialCoin) {
		return code.equals(potentialCoin.getCode());
	}
}
