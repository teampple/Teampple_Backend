package Backend.teampple.domain.teams.dto.response;

import Backend.teampple.domain.teams.entity.Team;
import Backend.teampple.domain.teams.vo.TeamVo;
import Backend.teampple.global.common.vo.DurationVo;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@EqualsAndHashCode
public class GetTeamDetailDto {
    @JsonUnwrapped
    private TeamVo teamVo;

    @ApiModelProperty(value = "팀원 수", example = "3", required = true)
    private int teammatesNum;

    @ApiModelProperty(value = "팀원 프로필 이미지", required = true)
    private List<String> teammatesImages;

    public static GetTeamDetailDto from(Team team, int teammatesNum, List<String> teammatesImages) {
        return GetTeamDetailDto.builder()
                .teamVo(TeamVo.of(team))
                .teammatesNum(teammatesNum)
                .teammatesImages(teammatesImages)
                .build();
    }
}
