package Backend.teampple.domain.teams.dto.response;

import Backend.teampple.domain.teams.dto.TeammateDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class GetTeammateDto {
    @ApiModelProperty(value = "본인 이름", example = "이름", required = true)
    private String name;

    @ApiModelProperty(value = "본인 학교", example = "학교", required = true)
    private String schoolName;

    @ApiModelProperty(value = "본인 전공", example = "찌리리공", required = true)
    private String major;

    @ApiModelProperty(value = "나머지 팀원", required = true)
    private List<TeammateDto> teammates;

    @Builder
    public GetTeammateDto(String name, String schoolName, String major, List<TeammateDto> teammates) {
        this.name = name;
        this.schoolName = schoolName;
        this.major = major;
        this.teammates = teammates;
    }
}
