package Backend.teampple.domain.invitations.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class GetInvitationValidationDto {
    @ApiModelProperty(notes = "초대 링크 유효 여부", example = "true", required = true)
    private boolean isValid;

    @ApiModelProperty(notes = "팀 이름", example = "바이에른 뮌헨", required = true)
    private String teamName;

    @Builder
    public GetInvitationValidationDto(boolean isValid, String teamName) {
        this.isValid = isValid;
        this.teamName = teamName;
    }
}
