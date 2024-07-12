package bo.umss.app.inventorySp.codeProduct.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import bo.umss.app.inventorySp.codeProduct.CodeProduct;
import bo.umss.app.inventorySp.exception.EmptyFieldException;
import bo.umss.app.inventorySp.line.model.Line;
import bo.umss.app.inventorySp.provider.model.Provider;

@Entity
@Table(name = "ntp_not_provided_provider", uniqueConstraints = { @UniqueConstraint(columnNames = { "ntp_code" }) })
public class NotProvidedProvider extends CodeProduct implements Serializable {

	private static final long serialVersionUID = -7571429198034102585L;

	public static String DEFAULT_PROVIDER_NAME = "Pepe";
	public static String DEFAULT_PROVIDER_PHONE_NUMBER = "71476576";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@SequenceGenerator(name = "np_not_provided_provider", sequenceName = "np_seq", initialValue = 1000)
	@Column(name = "ntp_id")
	private Long id;

	@NotNull
	@Column(name = "ntp_code")
	private String code;

	@NotNull
	@Column(name = "ntp_description")
	private String description;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "ln_id", nullable = false)
	private Line line;

	public NotProvidedProvider(String code, String description, Line line) {
		this.code = code;
		this.description = description;
		this.line = line;
	}

	public NotProvidedProvider() {
		super();
	}

	public static NotProvidedProvider at(String code, String description, Line line) {
		if (code.isEmpty())
			throw new EmptyFieldException(CODE_CAN_NOT_BE_BLANK);
		if (description.isEmpty())
			throw new EmptyFieldException(DESCRIPTION_CAN_NOT_BE_BLANK);
		if (null == line)
			throw new EmptyFieldException(LINE_CAN_NOT_BE_NULL);

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
		return Provider.at(DEFAULT_PROVIDER_NAME, DEFAULT_PROVIDER_PHONE_NUMBER);
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setLine(Line line) {
		this.line = line;
	}
}
