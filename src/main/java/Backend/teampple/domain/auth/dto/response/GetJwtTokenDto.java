package Backend.teampple.domain.auth.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Getter
@ToString
@NoArgsConstructor
public class GetJwtTokenDto {
    private String jwtAccessToken;
//    private String jwtRefreshToken;

    @Builder
    public GetJwtTokenDto(String jwtAccessToken, String jwtRefreshToken) {
        this.jwtAccessToken = jwtAccessToken;
//        this.jwtRefreshToken = jwtRefreshToken;
    }
}
