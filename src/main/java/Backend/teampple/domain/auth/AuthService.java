package Backend.teampple.domain.auth;

import Backend.teampple.domain.auth.dto.request.RequestJwtTokenDto;
import Backend.teampple.domain.auth.dto.response.ResponseJwtTokenDto;
import Backend.teampple.domain.users.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    ResponseJwtTokenDto login();

    void logout(User user, RequestJwtTokenDto requestJwtTokenDto);

    ResponseJwtTokenDto join();

    void withdrawal(User user);

    ResponseJwtTokenDto reIssuance(User user, RequestJwtTokenDto requestJwtTokenDto);
}
