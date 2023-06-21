package aiblue.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import aiblue.model.CasePositionCustom;
import aiblue.model.CasePositionCustom2;
import aiblue.service.rest.AIBlueService;

public class AIBlueApplication {

	public static void main(String[] args) {

		String uuid = "cac3cc18-001f-4993-bd25-01624eec70ae";
		AIBlueService aIBlueService = new AIBlueService("manu", "manu", "password", uuid, "WHITE");
		aIBlueService.joinGame();
		// List<Pieza> lista = aIBlueService.getBoard();
		// aIBlueService.move("H2", "H3");
		// verificar si el juego sigue o ha terminado para finalizar ejecución
		while (aIBlueService.isGameEndedStatus()) {
			// si es nuestro turno
			while (aIBlueService.isMyTurn()) {
				// Logica IA
				// consultar tablero
				// Calcular movimientos en arbol
				// aIBlueService.getAvailableMoves();
				// seleccionar 1 (implementar un timer de 1 minuto)
				// enviar nuestro movimiento
				Map<String, List<Map<String, CasePositionCustom2>>> moves = aIBlueService.getAvailableMoves(null,
						"WHITE");
				List<CasePositionCustom> list = new ArrayList<>();
				List<String> keys = new ArrayList<>(moves.keySet());

				for (String key : keys) {
					List<Map<String, CasePositionCustom2>> valueList = moves.get(key);
					for (int i = 0; i < valueList.size(); i++) {
						Map<String, CasePositionCustom2> finalValue = valueList.get(i);

						list.add(new CasePositionCustom(key, String.valueOf(finalValue.get("col")).toUpperCase()
								+ String.valueOf(finalValue.get("row"))));
					}

				}
				int random = (int) (Math.random() * moves.keySet().size() - 1) + 1;
				CasePositionCustom moveRandom;
				try {
					moveRandom = list.get(random);
				} catch (Exception ex) {
					moveRandom = list.get(list.size() - 1);
				}
				aIBlueService.move(moveRandom.getFrom(), moveRandom.getTo());
			}
		}
	}
}
