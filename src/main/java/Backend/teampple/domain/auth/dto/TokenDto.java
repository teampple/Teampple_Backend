package Backend.teampple.domain.auth.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter @ToString
@NoArgsConstructor
public class TokenDto {
    private String accessToken;
    private String refreshToken;

    @Builder
    public TokenDto(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;

    }
}
