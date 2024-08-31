package bo.umss.app.inventorySp.business.provider.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;

import bo.umss.app.inventorySp.exception.EmptyFieldException;

@Entity
@Table(name = "prv_provider", uniqueConstraints = { @UniqueConstraint(columnNames = { "prv_name" }) })
public class Provider implements Serializable {

	private static final long serialVersionUID = -481161483247606771L;

	public static final String NAME_CAN_NOT_BE_BLANK = "Name can not be blank.";
	public static final String PHONE_NUMBER_CAN_NOT_BE_BLANK = "Phone number can not be blank.";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@SequenceGenerator(name = "prvd_provided", sequenceName = "prv_seq", initialValue = 1000)
	@Column(name = "prv_id")
	private Long id;

	@NotBlank
	@Column(name = "prv_name")
	private String name;

	@NotBlank
	@Column(name = "prv_phone_number")
	private String phoneNumber;

	public Provider(String name, String phoneNumber) {
		this.name = name;
		this.phoneNumber = phoneNumber;
	}

	public Provider() {
	}

	public static Provider at(String name, String phoneNumber) {
		if (name.isEmpty())
			throw new EmptyFieldException(NAME_CAN_NOT_BE_BLANK);
		if (phoneNumber.isEmpty())
			throw new EmptyFieldException(PHONE_NUMBER_CAN_NOT_BE_BLANK);
		return new Provider(name, phoneNumber);
	}

	public String getName() {
		return name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public Boolean compareAnoherName(String potentialName) {
		return name.equalsIgnoreCase(potentialName);
	}

	public void setPhoneNumber(String potentialPhoneNumber) {
		phoneNumber = potentialPhoneNumber;
	}
}
