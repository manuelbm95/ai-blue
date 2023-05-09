package aiblue.service.rest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import aiblue.model.*;
import reactor.core.publisher.Mono;

@Service
public class AIBlueService {

// @Autowired
// private Environment env;
	private static final String URI_BEARER = "http://localhost:8080/oauth/token";
	
	//TODO cambiar void por un tipo de respuesta
	public void getTokenBearer(String user, String password, String grantType) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromPath(URI_BEARER);
		builder.queryParam("username", user);
		builder.queryParam("password", password);
		builder.queryParam("grant_type", grantType);

		BodyBearer body = new BodyBearer(user, password, grantType);
		String path = builder.build().toUriString();
		WebClient webClient = WebClient.create(path);
		
		//TODO falta quitar el void y recibir los datos del token para poder usarlo
		webClient.post()
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.header("Authorization", "Basic Y2xpZW50SWQ6c2VjcmV0")
				.body(Mono.just(body), BodyBearer.class)
				.retrieve()
				.bodyToFlux(Void.class).blockFirst();
	}
	
}
