package Backend.teampple.domain.teams.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class PostScheduleDto {
    @NotNull
    @ApiModelProperty(value = "팀플 이름", example = "teampple", required = true)
    private String name;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @ApiModelProperty(notes = "일정 마감일", example = "2023-01-01T11:22:33", required = true)
    private LocalDateTime dueDate;
}
