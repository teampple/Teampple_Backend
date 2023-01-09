package Backend.teampple.domain.teams.dto.response;

import Backend.teampple.domain.stages.entity.Stage;
import Backend.teampple.domain.tasks.entity.Task;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class GetTeamTasksDto {
    private String taskname;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd", timezone = "Asia/Seoul")
    private LocalDateTime startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd", timezone = "Asia/Seoul")
    private LocalDateTime dueDate;

    private int sequenceNum;

    private int totaltask;

    private int achievement;

    private List<getTaskDto> tasks;

    @Getter
    @Setter
    private class getTaskDto {
        private String name;

        private boolean isDone;

        public getTaskDto(Task task) {
            this.name = name;
            this.isDone = isDone;
        }
    }

    public GetTeamTasksDto(Stage stage) {
        taskname = stage.getTaskName();
        startDate = stage.getStartDate();
        dueDate = stage.getDueDate();
        sequenceNum = stage.getSequenceNum();
        totaltask = stage.getTotalTask();
        achievement = stage.getAchievement();
        tasks = stage.getTasks().stream()
                .map(task -> new getTaskDto(task))
                .collect(toList());
    }
}
