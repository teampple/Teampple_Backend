package Backend.teampple.domain.teams.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class DeleteTeammateDto {
    @NotNull
    @ApiModelProperty(notes = "삭제할 팀원 id", example = "1", required = true)
    private Long teammateId;

    @Builder
    public DeleteTeammateDto(Long teammateId) {
        this.teammateId = teammateId;
    }
}
