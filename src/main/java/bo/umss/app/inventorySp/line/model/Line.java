package bo.umss.app.inventorySp.line.model;

public class Line {

	public static final String NAME_CAN_NOT_BE_BLANK = "Name can not be blank";

	private String name;

	public Line(String name) {
		this.name = name;
	}

	public static Line at(String name) {
		if (name.isEmpty())
			throw new RuntimeException(NAME_CAN_NOT_BE_BLANK);

		return new Line(name);
	}

	public String getName() {
		return name;
	}
}
