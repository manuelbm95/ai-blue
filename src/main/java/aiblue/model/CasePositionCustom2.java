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
public class CasePositionCustom2 implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int x;

	private int y;

	private int row;

	private String col;

	private int colPos;
}
