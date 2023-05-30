package aiblue.model;

import java.util.Optional;

public enum EnumChest {

	STALEMATE("King under stalemate"), W_CHECKMATE("White king under checkmate"),
	B_CHECKMATE("Black king under checkmate"), REPETITIVE_MOVES("Same movements 3 times"),
	MATERIAL_DRAW("Not enough material for checkmate"), NO_END("Game is not ended yet");

	private final String cause;

	private EnumChest(String cause) {
		this.cause = cause;
	}

	public String getCause() {
		return cause;
	}

	public static Optional<EnumChest> searchByCause(String cause) {
		for (EnumChest value : EnumChest.values()) {
			if (value.getCause().equals(cause))
				return Optional.of(value);
		}
		return Optional.empty();
	}
}
