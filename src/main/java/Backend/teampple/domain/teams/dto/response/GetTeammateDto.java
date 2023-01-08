package Backend.teampple.domain.teams.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class GetTeammateDto {
    private String name;
    private String schoolName;
    private String major;

    @Builder
    public GetTeammateDto(String name, String schoolName, String major) {
        this.name = name;
        this.schoolName = schoolName;
        this.major = major;
    }
}
