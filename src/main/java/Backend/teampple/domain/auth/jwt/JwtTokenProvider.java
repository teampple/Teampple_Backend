package Backend.teampple.domain.auth.jwt;

import Backend.teampple.domain.auth.dto.response.ResponseTokenDto;
import Backend.teampple.domain.auth.security.CustomUserDetailServiceImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;

import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

//    @Value("${jwt.secret}")
//    private final String secretKey;
    private final String secretKey = "Teampple0Jwt0Super0Secret0Key2023abcdefghijklmn";
    private final CustomUserDetailServiceImpl customUserDetailService;

    /** 토큰 유효 시간 */
    private static final long JWT_EXPIRATION_TIME = 1000L * 60 * 60; /**60분*/
    private static final long REFRESH_TOKEN_EXPIRATION_TIME = 1000L * 60 * 60 * 24 * 14;/**2주*/

    private static final String AUTHORITIES_KEY = "auth";

    public Key getSecretKey(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }

    /** 토큰 생성 */
    public ResponseTokenDto generateToken(Authentication authentication)
            throws HttpServerErrorException.InternalServerError {
        final Date now = new Date();
        return ResponseTokenDto.builder()
                .accessToken(generateAccessToken(authentication, now))
                .refreshToken(generateRefreshToken(now))
                .build();
    }

    public String generateAccessToken(Authentication authentication, Date now) {
        final String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        final Date accessTokenExpire = new Date(now.getTime() + JWT_EXPIRATION_TIME);

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer("Teampple")
                .setIssuedAt(now) /**생성 일자*/
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities) /**권한 설정*/
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

    /**JWT 토큰을 복호화하여 토큰에 들어있는 정보를 꺼내는 메서드*/
    public Authentication getAuthentication(String token) {
        /** 토큰 복호화*/
        /** 사용자 속성값*/
        Claims claims = parseClaims(token);

        if (claims.get(AUTHORITIES_KEY) == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        /**userDetails 반환*/
        UserDetails userDetails = customUserDetailService.loadUserByUsername(
                claims.getSubject());
        log.info(userDetails.getUsername());
        return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), "",
                userDetails.getAuthorities());
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSecretKey()).build().parseClaimsJwt(token);
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
