package Backend.teampple.domain.invitations.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class GetInvitationDto {

    @NotNull
    @ApiModelProperty(notes = "초대 링크", example = "www.teampple.com/login/yYVezskm", required = true)
    private String url;

    @Builder
    public GetInvitationDto(String url) {
        this.url = url;
    }
}
