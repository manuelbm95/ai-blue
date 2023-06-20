package aiblue.application;

import aiblue.service.rest.AIBlueService;

public class AIBlueApplication {

	public static void main(String[] args) {
		String uuid = "7f8b9595-5696-4f56-a22a-e5d652a93073";
		AIBlueService aIBlueService = new AIBlueService("admin", "admin", "password", uuid, "WHITE");
		aIBlueService.joinGame();
		// verificar si el juego sigue o ha terminado para finalizar ejecución
		while (aIBlueService.isGameEndedStatus()) {
			// si es nuestro turno
			while (aIBlueService.isMyTurn()) {
				// Logica IA
				// consultar tablero
				// Calcular movimientos en arbol
				// seleccionar 1 (implementar un timer de 1 minuto)
				// enviar nuestro movimiento
			}
		}
	}
}
