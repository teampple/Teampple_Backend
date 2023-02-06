package Backend.teampple.domain.auth.inmemory.service;

import org.springframework.stereotype.Service;

@Service
public interface RefreshTokenService {
    void saveRefreshToken(String refreshToken, String authKey);
}
