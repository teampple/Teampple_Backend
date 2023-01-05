package Backend.teampple.domain.teams.dto.request;

import Backend.teampple.domain.stages.dto.request.PostStageDto;
import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(value = "팀플 이름", dataType = "string", required = true)
    private String name;

    @NotNull
    @ApiModelProperty(notes = "팀플 목표", dataType = "string", required = true)
    private String goal;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @ApiModelProperty(notes = "팀플 시작일", dataType = "string(yyyy-MM-dd'T'HH:mm:ss)", required = true)
    private LocalDateTime startDate;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @ApiModelProperty(notes = "팀플 마감일", dataType = "string(yyyy-MM-dd'T'HH:mm:ss)", required = true)
    private LocalDateTime dueDate;

    @Valid
    @Nullable
    @ApiModelProperty(value = "팀플 이름", dataType = "String name;\n" +
            "int sequenceNum;\n" +
            "string(yyyy-MM-dd'T'HH:mm:ss) startDate;\n" +
            "string(yyyy-MM-dd'T'HH:mm:ss) dueDate;", required = true)
    private List<PostStageDto> stages;
}
