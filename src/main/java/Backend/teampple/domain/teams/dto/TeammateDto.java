package Backend.teampple.domain.teams.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class TeammateDto {

    @NotNull
    @ApiModelProperty(notes = "팀원 이름", example = "이름", required = true)
    private String name;

    @NotNull
    @ApiModelProperty(notes = "팀원 학교", example = "학교", required = true)
    private String schoolName;

    @NotNull
    @ApiModelProperty(notes = "팀원 학과", example = "학과", required = true)
    private String major;

    @NotNull
    @ApiModelProperty(notes = "팀원 학과", example = "학과", required = true)
    private Long teammateId;

    @Builder
    public TeammateDto(String name, String schoolName, String major, Long teammateId) {
        this.name = name;
        this.schoolName = schoolName;
        this.major = major;
        this.teammateId = teammateId;
    }
}
