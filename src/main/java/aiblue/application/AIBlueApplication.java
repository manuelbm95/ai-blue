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
		 * Users: aiblueWhite aiblueBlack aiblueObserver
		 */
		String uuid = "008aa610-2427-4db8-a75f-97c67afe8d6d";
		String color = "WHITE";
		String user = "aiblueWhite";

		String filaPromotion = color.equals("WHITE") ? "8" : "1";
		String pawn = color.equals("WHITE") ? "White Pawn" : "Black Pawn";
		boolean promotion = false;
		AIBlueService aIBlueService = new AIBlueService(user, "DBC", "password", uuid, color);
		aIBlueService.joinGame();
		Optional<Pieza> p = Optional.empty();

		// verificar si el juego sigue o ha terminado para finalizar ejecución
		while (aIBlueService.isGameEndedStatus()) {
			// si es nuestro turno
			while (aIBlueService.isMyTurn()) {
				Map<String, List<Map<String, CasePositionCustom2>>> moves = aIBlueService.getAvailableMoves(null,
						color);

				List<Pieza> piezasList = new ArrayList<>();
				List<Pieza> piezasListAdversario = new ArrayList<>();

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
					if (color.equals("WHITE") && pi.getName() != null && !pi.getName().isEmpty()
							&& pi.getName().contains("Black")) {
						piezasListAdversario.add(pi);
					} else if (color.equals("BLACK") && pi.getName() != null && !pi.getName().isEmpty()
							&& pi.getName().contains("White")) {
						piezasListAdversario.add(pi);
					}
					piezasList.add(pi);
				});
				int random = (int) (Math.random() * list.size() - 1) + 1;
				List<CasePositionCustom> movimientoTop = new ArrayList<>();
				for (Pieza adversario : piezasListAdversario) {

					for (CasePositionCustom value : list) {

						if (value.getTo().equals(adversario.getRawPosition())) {
							movimientoTop.add(value);
						}
					}
				}

				boolean deadPossible = !movimientoTop.isEmpty();

				CasePositionCustom moveRandom;

				try {
					moveRandom = list.get(random);
				} catch (Exception ex) {
					moveRandom = list.get(list.size() - 1);
				}
				CasePositionCustom moveTop = null;

//				try {
//					Thread.sleep(2000);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}

				int randomDead = (int) (Math.random() * movimientoTop.size() - 1) + 1;
				try {
					if (deadPossible) {
						try {
							moveTop = movimientoTop.get(randomDead);
						} catch (Exception ex) {
							moveTop = movimientoTop.get(movimientoTop.size() - 1);
						}
						aIBlueService.move(moveTop.getFrom(), moveTop.getTo());
					} else {
						aIBlueService.move(moveRandom.getFrom(), moveRandom.getTo());
					}
				} catch (Exception e) {
					aIBlueService.move(moveRandom.getFrom(), moveRandom.getTo());
				}
				// pawn promotion
				board = aIBlueService.getBoard();
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
					final CasePositionCustom finalMove = deadPossible ? movimientoTop.get(randomDead) : moveRandom;
					p = piezasList.stream().filter(v -> v.getRawPosition().equals(finalMove.getFrom())).findFirst();
				} catch (Exception ex) {
					p = piezasList.stream().filter(v -> v.getRawPosition().equals(list.get(list.size() - 1).getFrom()))
							.findFirst();
				}
				promotion = p.isPresent() ? p.get().getName().equals(pawn) : false;
				if (promotion && deadPossible ? moveTop.getTo().contains(filaPromotion)
						: moveRandom.getTo().contains(filaPromotion)) {
					int piece = (int) (Math.random() * 3 - 1) + 1;
					if (!aIBlueService.movePromotion(moveRandom.getTo(), PawnPromotionPiecesModel.QUEEN.name())) {
					} else if (moveTop != null) {
						aIBlueService.movePromotion(moveTop.getTo(), PawnPromotionPiecesModel.QUEEN.name());
					}
				}
			}
//			try {
//				Thread.sleep(500);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
		}
		System.out.println("Se acabo el juego !");
	}
}
