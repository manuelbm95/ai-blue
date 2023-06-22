package aiblue.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import aiblue.enums.PawnPromotionPiecesModel;
import aiblue.model.CasePositionCustom;
import aiblue.model.CasePositionCustom2;
import aiblue.model.Pieza;
import aiblue.service.rest.AIBlueService;

public class AIBlueApplication {

	public static void main(String[] args) {
		/*
		 * aiblueWhite aiblueBlack aiblueObserver
		 */
		String uuid = "d2798ce0-6dcc-40f6-97e8-6dcbbbd01d55";
		String color = "WHITE";
		String filaPromotion = color.equals("WHITE") ? "8" : "1";
		String pawn = color.equals("WHITE") ? "White Pawn" : "Black Pawn";
		boolean promotion = false;
		AIBlueService aIBlueService = new AIBlueService("aiblueWhite", "DBC", "password", uuid, color);
		aIBlueService.joinGame();
		Optional<Pieza> p = Optional.empty();
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
						color);

				List<Pieza> piezasList = new ArrayList<>();

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
				int random = (int) Math.random() * list.size();
				CasePositionCustom moveRandom;

				try {
					moveRandom = list.get(random);
				} catch (Exception ex) {
					moveRandom = list.get(list.size() - 1);
				}

				aIBlueService.move(moveRandom.getFrom(), moveRandom.getTo());
				// pawn promotion
				List<Map<String, String>> board = aIBlueService.getBoard();
				board.forEach(value -> {
					Pieza pi = new Pieza();
					for (String key : value.keySet()) {
						if (key.equals("rawPosition")) {
							pi.setRawPosition(value.get(key));
						} else if (key.equals("name")) {
							pi.setName(value.get(key));
						}
					}
					piezasList.add(pi);
				});
				try {
					p = piezasList.stream().filter(v -> v.getRawPosition().equals(list.get(random).getFrom()))
							.findFirst();
				} catch (Exception ex) {
					p = piezasList.stream().filter(v -> v.getRawPosition().equals(list.get(list.size() - 1).getFrom()))
							.findFirst();
				}
				promotion = p.isPresent() ? p.get().getName().equals(pawn) : false;
				if (promotion && moveRandom.getTo().contains(filaPromotion)) {
					int piece = (int) (Math.random() * 3 - 1) + 1;
					if (!aIBlueService.movePromotion(moveRandom.getTo(),
							PawnPromotionPiecesModel.values()[piece].name())) {
					}
				}
			}
		}
	}
}
