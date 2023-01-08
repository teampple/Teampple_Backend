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
public class PostTeammateDto {
    @NotNull
    @ApiModelProperty(notes = "팀 id", example = "1", required = true)
    private Long teamId;
}
