package Backend.teampple.domain.invitations.dto.response;

import Backend.teampple.domain.teams.entity.Team;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostInvitationDto {
    @ApiModelProperty(notes = "팀 고유번호", example = "1", required = true)
    private Long teamId;

    public static PostInvitationDto from(Team team) {
        return PostInvitationDto.builder()
                .teamId(team.getId())
                .build();
    }
}
