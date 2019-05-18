package my.bootstart;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Member implements Serializable  {
	private long id;
	private String email;
	private String name;
}
