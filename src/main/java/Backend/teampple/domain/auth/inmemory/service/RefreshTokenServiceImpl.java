package Backend.teampple.domain.auth.inmemory.service;

import Backend.teampple.domain.auth.inmemory.RefreshToken;
import Backend.teampple.domain.auth.inmemory.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    @Transactional
    public void saveRefreshToken(String refreshToken, String authKey) {
        RefreshToken token = RefreshToken.builder()
                .jwtRefreshToken(refreshToken)
                .authKey(authKey)
                .build();
        refreshTokenRepository.save(token);
    }
}
