package Backend.teampple.domain.users.dto.response;

import lombok.*;

@Getter
@NoArgsConstructor
public class GetUserProfileDto {
    private String name;
    private String email;
    private String profileImage;
    private String schoolName;
    private String major;
    private String entranceYear;
    private String subscribePlan;

    @Builder
    public GetUserProfileDto(String name, String email, String profileImage, String schoolName, String major, String entranceYear, String subscribePlan) {
        this.name = name;
        this.email = email;
        this.profileImage = profileImage;
        this.schoolName = schoolName;
        this.major = major;
        this.entranceYear = entranceYear;
        this.subscribePlan = subscribePlan;
    }
}
