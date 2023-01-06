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
    @ApiModelProperty(value = "팀플 이름", dataType = "string", required = true)
    private String name;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @ApiModelProperty(notes = "일정 마감일", dataType = "string(yyyy-MM-dd'T'HH:mm:ss)", required = true)
    private LocalDateTime dueDate;
}
