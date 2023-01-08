package Backend.teampple.domain.teams.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class DeleteTeammateDto {
    @NotNull
    @ApiModelProperty(notes = "삭제할 팀원 id", dataType = "int", required = true)
    private Long teammateId;
}
