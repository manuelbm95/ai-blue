package aiblue.application;

import org.springframework.beans.factory.annotation.Autowired;

import aiblue.service.rest.AIBlueService;

public class AIBlueApplication {

	@Autowired
	private AIBlueService aIBlueService;
	
	public static void main(String[] args) {
		// verificar si el juego sigue o ha terminado para finalizar ejecución
		while(true) {
			// si es nuestro turno
			while(true) {
				//Logica IA
				//consultar tablero
				//Calcular movimientos en arbol
				//seleccionar 1 (implementar un timer de 1 minuto)
				// enviar nuestro movimiento
			}						
		}
	}
}
