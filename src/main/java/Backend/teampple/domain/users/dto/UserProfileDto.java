package Backend.teampple.domain.users.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserProfileDto {
    private String name;
    private String email;
    private String profileImage;
    private String schoolName;
    private String major;
    private String entranceYear;
    private String subscribePlan;
}
