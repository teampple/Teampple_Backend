package Backend.teampple.domain.auth;

import Backend.teampple.domain.auth.dto.request.RequestJwtTokenDto;
import Backend.teampple.domain.auth.dto.response.ResponseJwtTokenDto;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    ResponseJwtTokenDto login();

    void logout(Authentication authentication);

    ResponseJwtTokenDto join();

    void withdrawal(Authentication authentication);

    ResponseJwtTokenDto reIssuance(RequestJwtTokenDto requestJwtTokenDto);
}
