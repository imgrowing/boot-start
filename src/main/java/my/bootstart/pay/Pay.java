package my.bootstart.pay;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.joda.time.format.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "pay")
@ToString
public class Pay {
	private static final org.joda.time.format.DateTimeFormatter FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd hh:mm:ss");

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long amount;

	@Column(name = "tx_name")
	private String txName;

	@Column(name = "tx_date_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date txDateTime;

	public Pay(Long amount, String txName, String txDateTime) {
		this.amount = amount;
		this.txName = txName;
		this.txDateTime = FORMATTER.parseDateTime(txDateTime).toDate();
	}

	public Pay(Long id, Long amount, String txName, String txDateTime) {
		this.id = id;
		this.amount = amount;
		this.txName = txName;
		this.txDateTime = FORMATTER.parseDateTime(txDateTime).toDate();
	}
}
