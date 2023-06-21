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
public class CasePositionCustom implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String from;
	private String to;
}
