package Backend.teampple.domain.auth;

import Backend.teampple.domain.auth.dto.request.RequestJwtTokenDto;
import Backend.teampple.domain.auth.dto.request.RequestOAuthTokenDto;
import Backend.teampple.domain.auth.dto.response.ResponseTokenDto;
import Backend.teampple.domain.users.dto.request.PostUserProfileDto;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    ResponseTokenDto login(RequestOAuthTokenDto requestOAuthTokenDto);

    void logout(String refreshToken);

    ResponseTokenDto join(RequestOAuthTokenDto requestOAuthTokenDto, PostUserProfileDto postUserProfileDto);

    void withdrawal(String refreshToken);

    ResponseTokenDto reIssuance(RequestJwtTokenDto requestJwtTokenDto);
}
