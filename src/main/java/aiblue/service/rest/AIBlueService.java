package aiblue.service.rest;

import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import aiblue.model.BodyBearer;
import aiblue.model.EnumChest;
import aiblue.model.TokenBearer;
import reactor.core.publisher.Mono;

public class AIBlueService {

	private static final String URI_BEARER = "http://localhost:8080/oauth/token";
	private static final String URI_ENDGAME = "http://localhost:8080/v1/game/game-ended";
	private static final String URI_TURN = "http://localhost:8080/v1/game/player-turn";
	private static final String UUID_PARAM = "uuid";

	private TokenBearer token;
	private String user, password, grantType;
	private String uuid;

	public AIBlueService(String user, String password, String grantType) {
		this.user = user;
		this.password = password;
		this.grantType = grantType;
		getTokenBearer();
	}

	public void getTokenBearer() {
		UriComponentsBuilder builder = UriComponentsBuilder.fromPath(URI_BEARER);
		builder.queryParam("username", this.user);
		builder.queryParam("password", this.password);
		builder.queryParam("grant_type", this.grantType);

		BodyBearer body = new BodyBearer(this.user, this.password, this.grantType);
		String path = builder.build().toUriString();
		WebClient webClient = WebClient.create(path);
		this.token = webClient.post().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.header("Authorization", "Basic Y2xpZW50SWQ6c2VjcmV0").body(Mono.just(body), BodyBearer.class)
				.retrieve().bodyToFlux(TokenBearer.class).blockFirst();
	}

	private boolean endingStates(String uuid, EnumChest toEvaluate) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromPath(URI_ENDGAME);
		builder.queryParam(UUID_PARAM, uuid);
		WebClient webClient = WebClient.create(builder.build().toUriString());
		Optional<EnumChest> value = EnumChest
				.searchByCause(webClient.get().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
						.header("Authorization", "Basic " + this.token.getAccessToken()).retrieve()
						.bodyToFlux(String.class).blockFirst());
		return value.isPresent() ? value.get().getCause().equals(toEvaluate.getCause()) : false;
	}

	public boolean isGameEndedStatus(String uuid) {
		return endingStates(uuid, EnumChest.NO_END);
	}

	public boolean isMyTurn() {
		UriComponentsBuilder builder = UriComponentsBuilder.fromPath(URI_TURN);
		builder.queryParam(UUID_PARAM, uuid);
		WebClient webClient = WebClient.create(builder.build().toUriString());
		return webClient.get().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.header("Authorization", "Basic " + this.token.getAccessToken()).retrieve().bodyToFlux(Boolean.class)
				.blockFirst();
	}
}
