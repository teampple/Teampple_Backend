package Backend.teampple.domain.users.dto.response;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetUserProfileDto {
    private String name;
    private String email;
    private String profileImage;
    private String schoolName;
    private String major;
    private String entranceYear;
    private String subscribePlan;
}
