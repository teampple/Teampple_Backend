package Backend.teampple.domain.auth.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@ToString
public class PostJwtTokenDto {
    @NotNull
    @ApiModelProperty(value = "서버로부터 받은 refreshToken", required = true)
    private String jwtRefreshToken;
}
