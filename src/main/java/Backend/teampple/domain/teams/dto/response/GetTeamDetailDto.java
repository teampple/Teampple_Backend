package Backend.teampple.domain.teams.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class GetTeamDetailDto {
    @ApiModelProperty(value = "팀플 이름", example = "teampple", required = true)
    private String name;

    @ApiModelProperty(value = "팀플 목표", example = "목포", required = true)
    private String goal;

    @ApiModelProperty(value = "팀플 시작일", example = "2023-01-01T11:22:33", required = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd", timezone = "Asia/Seoul")
    private LocalDateTime startDate;

    @ApiModelProperty(value = "팀플 마감일", example = "2023-01-01T11:22:33", required = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd", timezone = "Asia/Seoul")
    private LocalDateTime dueDate;

    @ApiModelProperty(value = "팀원 수", example = "3", required = true)
    private int teammatesNum;

    @ApiModelProperty(value = "팀원 프로필 이미지", required = true)
    private List<String> teammatesImages;

    @Builder
    public GetTeamDetailDto(String name, String goal, LocalDateTime startDate, LocalDateTime dueDate,
                            int teammatesNum, List<String> teammatesImages) {
        this.name = name;
        this.goal = goal;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.teammatesNum = teammatesNum;
        this.teammatesImages = teammatesImages;
    }
}
