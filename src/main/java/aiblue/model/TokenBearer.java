package aiblue.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class TokenBearer implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonProperty("access_token")
	private String accessToken;
	@JsonProperty("token_type")
    private String tokenType;
	@JsonProperty("refresh_token")
    private String refreshToken;
	@JsonProperty("expires_in")
    private Integer expiresIn;
	@JsonProperty("scope")
    private String scope;

}
