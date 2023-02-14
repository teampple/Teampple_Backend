package Backend.teampple.domain.teams.dto.request;

import Backend.teampple.domain.teams.vo.ScheduleVo;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class PostScheduleDto {
    @JsonUnwrapped
    ScheduleVo scheduleVo;
}
