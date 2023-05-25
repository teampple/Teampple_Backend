package Backend.teampple.domain.auth;

import Backend.teampple.domain.auth.dto.request.PostJwtTokenDto;
import Backend.teampple.domain.auth.dto.response.GetJwtTokenDto;
import Backend.teampple.domain.users.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    GetJwtTokenDto login();

    void logout(User user, PostJwtTokenDto postJwtTokenDto);

    GetJwtTokenDto join();

    void withdrawal(User user);

    GetJwtTokenDto reIssuance(User user, PostJwtTokenDto postJwtTokenDto);
}
