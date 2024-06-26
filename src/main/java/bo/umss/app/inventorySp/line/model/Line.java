package bo.umss.app.inventorySp.line.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "line")
public class Line implements Serializable {

	private static final long serialVersionUID = -8555506285688382910L;

	public static final String NAME_CAN_NOT_BE_BLANK = "Name can not be blank";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@SequenceGenerator(name = "ln_line", sequenceName = "ln_seq", initialValue = 1000)
	@Column(name = "ID")
	private Long id;

	@NotNull
	@Column(name = "NAME")
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

	public Boolean compareOtherName(String potentialName) {
		return name.equalsIgnoreCase(potentialName);
	}

	public Long getId() {
		return id;
	}

	public void setName(String potentialName) {
		name = potentialName;
	}
}
