package Backend.teampple.domain.users.dto.request;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PutUserProfileDto {
    private String schoolName;
    private String major;
    private String entranceYear;
}
