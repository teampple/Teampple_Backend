package Backend.teampple.domain.users.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PutUserProfileDto {
    @ApiModelProperty(value = "학교 이름", example = "schoolU1x")
    private String schoolName;
    @ApiModelProperty(value = "전공 이름", example = "majorU1x")
    private String major;
    @ApiModelProperty(value = "입학년도", example = "entYearU1x")
    private String entranceYear;
}
