package Backend.teampple.domain.auth.oauth.dto;

import lombok.*;

@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class KakaoAccountDto {
    private boolean has_email;

    private boolean email_needs_agreement;

    private boolean is_email_valid;

    private boolean is_email_verified;

    private String email;

    private KakaoProfileDto profile;
}
