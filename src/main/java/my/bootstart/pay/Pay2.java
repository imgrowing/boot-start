package my.bootstart.pay;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "pay2")
@ToString
public class Pay2 {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long amount;

	@Column(name = "tx_name")
	private String txName;

	@Column(name = "tx_date_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date txDateTime;

	public Pay2(Long amount, String txName, Date txDateTime) {
		this.amount = amount;
		this.txName = txName;
		this.txDateTime = txDateTime;
	}

	public Pay2(Long id, Long amount, String txName, Date txDateTime) {
		this.id = id;
		this.amount = amount;
		this.txName = txName;
		this.txDateTime = txDateTime;
	}
}
