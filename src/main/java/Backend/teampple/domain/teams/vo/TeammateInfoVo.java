package Backend.teampple.domain.teams.vo;

import Backend.teampple.domain.teams.entity.Teammate;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@Builder
public class TeammateInfoVo {
    @JsonUnwrapped
    private TeammateNameInfoVo teammateNameInfoVo;

    @NotNull
    @ApiModelProperty(notes = "팀원 학교", example = "학교", required = true)
    private String schoolName;

    @NotNull
    @ApiModelProperty(notes = "팀원 학과", example = "학과", required = true)
    private String major;


    @NotNull
    @ApiModelProperty(notes = "팀원 이미지", example = "1", required = true)
    private String image;

    public static TeammateInfoVo from(Teammate teammate) {
        return TeammateInfoVo.builder()
                .teammateNameInfoVo(TeammateNameInfoVo.from(teammate))
                .schoolName(teammate.getUserProfile().getSchoolName())
                .major(teammate.getUserProfile().getMajor())
                .image(teammate.getUserProfile().getProfileImage())
                .build();
    }
}
