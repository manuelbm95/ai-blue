package aiblue.application;

import aiblue.service.rest.AIBlueService;

public class AIBlueApplication {

	public static void main(String[] args) {
		String uuid = "3918ee57-22db-4e59-aeda-e2ccb74692a6";
		AIBlueService aIBlueService = new AIBlueService("manu", "manu", "password", uuid, "WHITE");
		// aIBlueService.joinGame();
		// List<Pieza> lista = aIBlueService.getBoard();
		// aIBlueService.move("H2", "H3");
		// verificar si el juego sigue o ha terminado para finalizar ejecución
		while (aIBlueService.isGameEndedStatus()) {
			// si es nuestro turno
			while (aIBlueService.isMyTurn()) {
				// Logica IA
				// consultar tablero
				// Calcular movimientos en arbol
				//aIBlueService.getAvailableMoves();
				// seleccionar 1 (implementar un timer de 1 minuto)
				// enviar nuestro movimiento
			}
		}
	}
}
