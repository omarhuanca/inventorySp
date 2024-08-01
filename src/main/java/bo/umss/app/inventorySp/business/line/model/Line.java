package bo.umss.app.inventorySp.business.line.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import bo.umss.app.inventorySp.exception.EmptyFieldException;

@Entity
@Table(name = "ln_line", uniqueConstraints = { @UniqueConstraint(columnNames = { "ln_name" }) })
public class Line implements Serializable {

	private static final long serialVersionUID = -8555506285688382910L;

	public static final String NAME_CAN_NOT_BE_BLANK = "Name can not be blank";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@SequenceGenerator(name = "lne_line", sequenceName = "ln_seq", initialValue = 1000)
	@Column(name = "ln_id")
	private Long id;

	@NotNull
	@Column(name = "ln_name")
	private String name;

	public Line(String name) {
		this.name = name;
	}

	public Line() {
		super();
	}

	public static Line at(String name) {
		if (name.isEmpty())
			throw new EmptyFieldException(NAME_CAN_NOT_BE_BLANK);

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

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String potentialName) {
		name = potentialName;
	}
}
