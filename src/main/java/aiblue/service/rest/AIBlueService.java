package aiblue.service.rest;

import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import aiblue.model.BodyBearer;
import aiblue.model.EnumChest;
import aiblue.model.TokenBearer;
import reactor.core.publisher.Mono;

public class AIBlueService {

	private static final String BASE_URL = "http://localhost:8080/";
	private static final String URI_BEARER = "oauth/token";
	private static final String URI_ENDGAME = "api/v1/game/game-ended";
	private static final String URI_TURN = "api/v1/game/player-turn";
	private static final String URI_JOIN = "api/v1/game/join";
	private static final String UUID_PARAM = "uuid";

	private TokenBearer token;
	private String user, password, grantType;
	private String uuid;
	private String playerColor;
	private WebClient webClient;

	public AIBlueService(String user, String password, String grantType, String uuid, String playerColor) {
		this.user = user;
		this.password = password;
		this.grantType = grantType;
		this.uuid = uuid;
		this.playerColor = playerColor;
		getTokenBearer();
	}

	public void getTokenBearer() {
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(URI_BEARER);
		builder.queryParam("username", this.user);
		builder.queryParam("password", this.password);
		builder.queryParam("grant_type", this.grantType);

		BodyBearer body = new BodyBearer(this.user, this.password, this.grantType);
		webClient = WebClient.create(BASE_URL + builder.build().toUriString());
		this.token = webClient.post().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.header("Authorization", "Basic Y2xpZW50SWQ6c2VjcmV0").body(Mono.just(body), BodyBearer.class)
				.retrieve().bodyToFlux(TokenBearer.class).blockFirst();
	}

	public void joinGame() {
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(URI_JOIN);
		MultiValueMap<String, String> valueMap = new LinkedMultiValueMap<>();
		valueMap.add(UUID_PARAM, this.uuid);
		valueMap.add("side", this.playerColor);
		valueMap.add("uiUuid", this.uuid);
		webClient = WebClient.create(BASE_URL + builder.build().toUriString());
		webClient.post().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
				.header("Authorization", "Bearer " + this.token.getAccessToken())
				.body(BodyInserters.fromFormData(valueMap)).retrieve().bodyToFlux(Void.class).blockFirst();
	}

	private boolean endingStates(EnumChest toEvaluate) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(URI_ENDGAME);
		builder.queryParam(UUID_PARAM, this.uuid);
		webClient = WebClient.create(BASE_URL + builder.build().toUriString());
		Optional<EnumChest> value = EnumChest
				.searchByCause(webClient.get().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
						.header("Authorization", "Bearer " + this.token.getAccessToken()).retrieve()
						.bodyToFlux(String.class).blockFirst());
		return value.isPresent() ? value.get().getCause().equals(toEvaluate.getCause()) : false;
	}

	public boolean isGameEndedStatus() {
		return endingStates(EnumChest.NO_END);
	}

	public boolean isMyTurn() {
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(URI_TURN);
		builder.queryParam(UUID_PARAM, this.uuid);
		webClient = WebClient.create(BASE_URL + builder.build().toUriString());
		return webClient.get().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.header("Authorization", "Bearer " + this.token.getAccessToken()).retrieve().bodyToFlux(Boolean.class)
				.blockFirst();
	}
}
