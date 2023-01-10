package Backend.teampple.domain.users.dto.request;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PostUserProfileDto {
    private String name;
    private String schoolName;
    private String major;
}
