package bo.umss.app.inventorySp.provider.model;

public class Provider {

	public static final String NAME_CAN_NOT_BE_BLANK = "Name can not be blank.";
	public static final String PHONE_NUMBER_CAN_NOT_BE_BLANK = "Phone number can not be blank.";

	private String name;
	private String phoneNumber;

	public Provider(String name, String phoneNumber) {
		this.name = name;
		this.phoneNumber = phoneNumber;
	}

	public static Provider at(String name, String phoneNumber) {
		if (name.isEmpty())
			throw new RuntimeException(NAME_CAN_NOT_BE_BLANK);
		if (phoneNumber.isEmpty())
			throw new RuntimeException(PHONE_NUMBER_CAN_NOT_BE_BLANK);
		return new Provider(name, phoneNumber);
	}

	public String getName() {
		return name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

}
