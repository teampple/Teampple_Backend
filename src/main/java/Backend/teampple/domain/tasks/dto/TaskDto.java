package Backend.teampple.domain.tasks.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class TaskDto {

    @NotNull
    @ApiModelProperty(notes = "할 일 이름", example = "이름", required = true)
    private String name;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    @ApiModelProperty(notes = "할 일 시작일", example = "2023-01-01T11:22:33", required = true)
    private LocalDateTime startDate;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    @ApiModelProperty(notes = "할 일 마감일", example = "2023-01-01T11:22:33", required = true)
    private LocalDateTime dueDate;

    @NotNull
    @ApiModelProperty(notes = "할 일 담당자", required = true)
    private List<Long> operators;
}
