package Backend.teampple.domain.teams.vo;

import Backend.teampple.domain.teams.entity.Teammate;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@Builder
public class TeammateNameInfoVo {


    @NotNull
    @ApiModelProperty(notes = "팀원 이름", example = "이름", required = true)
    private String name;

    @NotNull
    @ApiModelProperty(notes = "팀원 고유번호", example = "1", required = true)
    private Long id;

    public static TeammateNameInfoVo from(Teammate teammate) {
        return TeammateNameInfoVo.builder()
                .name(teammate.getUserProfile().getName())
                .id(teammate.getId())
                .build();
    }

}
