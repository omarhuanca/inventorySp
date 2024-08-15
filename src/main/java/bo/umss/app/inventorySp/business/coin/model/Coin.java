package bo.umss.app.inventorySp.business.coin.model;

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

@Entity
@Table(name = "cn_coin", uniqueConstraints = { @UniqueConstraint(columnNames = { "cn_code" }) })
public class Coin implements Serializable {

	private static final long serialVersionUID = -2443110454635682595L;

	public static final String CODE_CAN_NOT_BE_BLANK = "Code can not be blank";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@SequenceGenerator(name = "con_coin", sequenceName = "cn_seq", initialValue = 1000)
	@Column(name = "cn_id")
	private Long id;

	@NotNull
	@Column(name = "cn_code")
	private String code;

	private Coin(String code) {
		this.code = code;
	}

	public Coin() {
	}

	public static Coin at(String code) {
		if (code.isEmpty())
			throw new RuntimeException(CODE_CAN_NOT_BE_BLANK);

		return new Coin(code);
	}

	public Long getId() {
		return id;
	}

	public String getCode() {
		return code;
	}

	public Boolean compareCode(Coin potentialCoin) {
		return code.equals(potentialCoin.getCode());
	}

	public Boolean compareOtherCode(String potentialCode) {
		return code.equalsIgnoreCase(potentialCode);
	}
}
