package Backend.teampple.domain.auth.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestSignUpDto {
    @NonNull
    @ApiModelProperty(value = "kakao 로그인 고유 id", example = "kakaoU1", required = true)
    private String idToken;
    @NonNull
    @ApiModelProperty(value = "kakao accessToken", example = "string", required = true)
    private String oauthAccessToken;
    @NonNull
    @ApiModelProperty(value = "kakao refreshToken", example = "string", required = true)
    private String oauthRefreshToken;

    @NotNull
    @ApiModelProperty(value = "사용자 이름", example = "U100", required = true)
    private String name;
    @NotNull
    @ApiModelProperty(value = "프로필 이미지 주소", example = "proImageU100", required = true)
    private String profileImage;
    @ApiModelProperty(value = "학교 이름", example = "schoolU100")
    private String schoolName;
    @ApiModelProperty(value = "전공이름", example = "majorU100")
    private String major;
}

