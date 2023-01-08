package Backend.teampple.domain.teams.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class TeammateDto {
    private String name;
    private String schoolName;
    private String major;

    @Builder
    public TeammateDto(String name, String schoolName, String major) {
        this.name = name;
        this.schoolName = schoolName;
        this.major = major;
    }
}
