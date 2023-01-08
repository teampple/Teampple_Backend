package Backend.teampple.domain.teams.dto.response;

import Backend.teampple.domain.teams.dto.TeammateDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class GetTeammateDto {
    private String name;
    private String schoolName;
    private String major;
    private List<TeammateDto> teammates;

    @Builder
    public GetTeammateDto(String name, String schoolName, String major, List<TeammateDto> teammates) {
        this.name = name;
        this.schoolName = schoolName;
        this.major = major;
        this.teammates = teammates;
    }
}
