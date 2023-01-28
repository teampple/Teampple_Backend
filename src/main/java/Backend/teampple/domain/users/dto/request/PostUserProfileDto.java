package Backend.teampple.domain.users.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PostUserProfileDto {
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
