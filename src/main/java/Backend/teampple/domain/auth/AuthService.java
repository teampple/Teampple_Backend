package Backend.teampple.domain.auth;

import Backend.teampple.domain.auth.dto.request.RequestJwtTokenDto;
import Backend.teampple.domain.auth.dto.request.RequestOAuthTokenDto;
import Backend.teampple.domain.auth.dto.request.RequestSignUpDto;
import Backend.teampple.domain.auth.dto.response.ResponseTokenDto;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    ResponseTokenDto login(RequestOAuthTokenDto requestOAuthTokenDto);

    void logout(Authentication authentication);

    ResponseTokenDto join(RequestSignUpDto requestSignUpDto);

    void withdrawal(Authentication authentication);

    ResponseTokenDto reIssuance(RequestJwtTokenDto requestJwtTokenDto);
}
