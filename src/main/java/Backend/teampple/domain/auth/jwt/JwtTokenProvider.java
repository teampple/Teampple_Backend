package Backend.teampple.domain.auth.jwt;

import Backend.teampple.domain.auth.jwt.entity.JwtToken;
import Backend.teampple.domain.auth.security.CustomUserDetailServiceImpl;
import Backend.teampple.domain.auth.security.UserAdapter;
import Backend.teampple.global.error.ErrorCode;
import Backend.teampple.global.error.exception.UnauthorizedException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;

import java.security.Key;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${token.secret}")
    private String secretKey;
    private final CustomUserDetailServiceImpl customUserDetailService;

    /**
     * 토큰 유효 시간
     */
    /**
     * 60분
     */
    private static final long JWT_EXPIRATION_TIME = 1000L * 60 * 60;
    /**
     * 2주
     */
    private static final long REFRESH_TOKEN_EXPIRATION_TIME = 1000L * 60 * 60 * 24 * 14;

    private static final String AUTHORITIES_KEY = "auth";

    public Key getSecretKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    /**
     * 토큰 생성
     */
    public JwtToken generateToken(String authKey)
            throws HttpServerErrorException.InternalServerError {
        final Date now = new Date();
        return JwtToken.builder()
                .jwtAccessToken(generateAccessToken(authKey, now))
                .jwtRefreshToken(generateRefreshToken(now))
                .build();
    }

    public String generateAccessToken(String authKey, Date now) {
        final Date accessTokenExpire = new Date(now.getTime() + JWT_EXPIRATION_TIME);

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer("Teampple")
                .setIssuedAt(now) /**생성 일자*/
                .setSubject(authKey)
                .claim(AUTHORITIES_KEY, authKey) /**권한 설정*/
                .setExpiration(accessTokenExpire) /**만료 일자*/
                .signWith(getSecretKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(Date now) {
        final Date refreshTokenExpire = new Date(now.getTime() + REFRESH_TOKEN_EXPIRATION_TIME);

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer("Teampple")
                .setExpiration(refreshTokenExpire)
                .signWith(getSecretKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**토큰 유효성 확인*/

    /**
     * JWT 토큰을 복호화하여 토큰에 들어있는 정보를 꺼내는 메서드
     */
    public Authentication getAuthentication(String token) {
        /** 토큰 복호화*/
        /** 사용자 속성값*/
        Claims claims = parseClaims(token);

        if (claims.get(AUTHORITIES_KEY) == null) {
            throw new UnauthorizedException(ErrorCode.INVALID_AUTH_TOKEN);
        }
        ;
        log.info(claims.getSubject());
        /**userDetails 반환*/
        UserAdapter userDetails = (UserAdapter) customUserDetailService
                .loadUserByUsername(claims.getSubject());

        return new UsernamePasswordAuthenticationToken(userDetails.getUser(), userDetails.getPassword(),
                userDetails.getAuthorities());
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSecretKey()).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.", e);
        }
        return false;
    }

    private Claims parseClaims(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(getSecretKey()).build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}
