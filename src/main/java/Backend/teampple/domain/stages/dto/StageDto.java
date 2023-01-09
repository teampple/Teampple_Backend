package Backend.teampple.domain.stages.dto;


import Backend.teampple.domain.stages.entity.Stage;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class StageDto {
    @NotNull
    @ApiModelProperty(notes = "단계 명", example = "1단계", required = true)
    private String name;

    @NotNull
    @Min(1)
    @ApiModelProperty(notes = "단계 순서", example = "1", required = true)
    private int sequenceNum;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    @ApiModelProperty(notes = "단계 시작일", example = "2023-01-01T11:22:33", required = true)
    private LocalDateTime startDate;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    @ApiModelProperty(notes = "단계 마감일", example = "2023-01-01T11:22:33", required = true)
    private LocalDateTime dueDate;

    @Builder
    public StageDto(String name, int sequenceNum, LocalDateTime startDate, LocalDateTime dueDate) {
        this.name = name;
        this.sequenceNum = sequenceNum;
        this.startDate = startDate;
        this.dueDate = dueDate;
    }

    public StageDto(Stage stage) {
        this.name = stage.getTaskName();
        this.sequenceNum = stage.getSequenceNum();
        this.startDate = stage.getStartDate();
        this.dueDate = stage.getDueDate();
    }

}
