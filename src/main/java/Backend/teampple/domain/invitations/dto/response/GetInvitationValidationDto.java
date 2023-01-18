package Backend.teampple.domain.invitations.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class GetInvitationValidationDto {

    @NotNull
    @ApiModelProperty(notes = "초대 링크 유효 여부", example = "true", required = true)
    private boolean isValid;

    @NotNull
    @ApiModelProperty(notes = "팀 이름", example = "바이에른 뮌헨", required = true)
    private String teamName;

    @Builder
    public GetInvitationValidationDto(boolean isValid, String teamName) {
        this.isValid = isValid;
        this.teamName = teamName;
    }
}
