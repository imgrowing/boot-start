package my.bootstart.partition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(of = {"id", "loginId"})
@AllArgsConstructor
public class User {
	private int id;
	private String loginId;
	private String name;
}
