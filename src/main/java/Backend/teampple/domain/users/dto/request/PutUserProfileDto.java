package Backend.teampple.domain.users.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@NoArgsConstructor
public class PutUserProfileDto {
    @ApiModelProperty(value = "사용자 이름", example = "U12345")
    private String name;
    @ApiModelProperty(value = "사용자 이메일 주소", example = "U12345@")
    private String email;
    @ApiModelProperty(value = "학교 이름", example = "schoolU1x")
    private String schoolName;
    @ApiModelProperty(value = "전공 이름", example = "majorU1x")
    private String major;
    @ApiModelProperty(value = "입학년도", example = "entYearU1x")
    private String entranceYear;
}
