package Backend.teampple.domain.teams.dto.response;

import Backend.teampple.domain.teams.vo.TeammateInfoVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class GetTeammateDto {
    @ApiModelProperty(value = "본인 고유번호", example = "1", required = true)
    private Long teammateId;

    @ApiModelProperty(value = "본인 이름", example = "이름", required = true)
    private String name;

    @ApiModelProperty(value = "본인 학교", example = "학교", required = true)
    private String schoolName;

    @ApiModelProperty(value = "본인 전공", example = "찌리리공", required = true)
    private String major;

    @ApiModelProperty(value = "본인 이미지", example = "1", required = true)
    private String image;

    @ApiModelProperty(value = "나머지 팀원", required = true)
    private List<TeammateInfoVo> teammateInfoVos;

    @Builder
    public GetTeammateDto(Long teammateId, String name, String schoolName, String major,
                          String image, List<TeammateInfoVo> teammateInfoVos) {
        this.teammateId = teammateId;
        this.name = name;
        this.schoolName = schoolName;
        this.major = major;
        this.image = image;
        this.teammateInfoVos = teammateInfoVos;
    }
}
