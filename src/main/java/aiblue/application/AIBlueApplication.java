package aiblue.application;

import aiblue.service.rest.AIBlueService;

public class AIBlueApplication {

	public static void main(String[] args) {
		AIBlueService aIBlueService = new AIBlueService("admin", "admin", "password");
		// verificar si el juego sigue o ha terminado para finalizar ejecución
		String uuid = "607f828e-07b8-4640-94af-eca0601f9f47";
		while (!aIBlueService.isGameEndedStatus(uuid)) {
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
