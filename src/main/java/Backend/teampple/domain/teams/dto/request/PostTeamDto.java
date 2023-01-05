package Backend.teampple.domain.teams.dto.request;

import Backend.teampple.domain.stages.dto.request.PostStageDto;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

/**
 *  팀 생성에 사용하는 request dto 입니다.
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class PostTeamDto {
    @NotNull
    private String name;

    @NotNull
    private String goal;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startDate;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dueDate;

    @Valid
    @Nullable
    private List<PostStageDto> stages;
}
