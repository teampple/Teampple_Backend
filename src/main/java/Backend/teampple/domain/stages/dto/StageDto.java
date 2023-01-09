package Backend.teampple.domain.stages.dto;


import Backend.teampple.domain.stages.entity.Stage;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class StageDto {
    @NotNull
    private String name;

    @NotNull
    private int sequenceNum;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime startDate;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
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
