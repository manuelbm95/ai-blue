package aiblue.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Pieza implements Serializable {

	private static final long serialVersionUID = 1L;

	private String rawPosition;
	private boolean side;
	private String name;
}
