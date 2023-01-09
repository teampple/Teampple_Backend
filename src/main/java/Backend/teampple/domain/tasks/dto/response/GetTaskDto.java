package Backend.teampple.domain.tasks.dto.response;

import Backend.teampple.domain.feedbacks.dto.FeedbackDto;
import Backend.teampple.domain.files.dto.FileInfoDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class GetTaskDto {
    // task
    @NotNull
    @ApiModelProperty(notes = "할 일 이름", example = "이름", required = true)
    private String taskName;

    @NotNull
    @ApiModelProperty(notes = "할 일 마감 여부", example = "false", required = true)
    private boolean isDone;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    @ApiModelProperty(notes = "할 일 시작일", example = "2023-01-01T11:22:33", required = true)
    private LocalDateTime startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    @ApiModelProperty(notes = "할 일 마감일", example = "2023-01-01T11:22:33", required = true)
    private LocalDateTime dueDate;

    // -----------------------------------
    // stage
    @ApiModelProperty(notes = "단계 이름", example = "단계", required = true)
    private String stageName;

    @ApiModelProperty(notes = "단계 순서", example = "1", required = true)
    private int sequenceNum;

    // -----------------------------------
    // operator & user
    @ApiModelProperty(notes = "담당자 이름", example = "뽀로로, 크롱", required = true)
    private List<String> operators;

    // -----------------------------------
    // file
    @ApiModelProperty(notes = "파일 정보", required = true)
    private List<FileInfoDto> files;

    // -----------------------------------
    // feedback & user
    @ApiModelProperty(notes = "피드백 정보", required = true)
    private List<FeedbackDto> feedbacks;

    @Builder
    public GetTaskDto(String taskName, boolean isDone, LocalDateTime startDate,
                      LocalDateTime dueDate, String stageName, int sequenceNum,
                      List<String> operators, List<FileInfoDto> files, List<FeedbackDto> feedbacks) {
        this.taskName = taskName;
        this.isDone = isDone;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.stageName = stageName;
        this.sequenceNum = sequenceNum;
        this.operators = operators;
        this.files = files;
        this.feedbacks = feedbacks;
    }
}
