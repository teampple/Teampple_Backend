package Backend.teampple.domain.auth.mapper;

import Backend.teampple.domain.auth.dto.request.RequestOAuthTokenDto;
import Backend.teampple.domain.auth.dto.request.RequestSignUpDto;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-01-18T23:02:31+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 11.0.15 (OpenLogic)"
)
@Component
public class RequestOAuthTokenMapperImpl implements RequestOAuthTokenMapper {

    @Override
    public RequestSignUpDto toDto(RequestOAuthTokenDto e) {
        if ( e == null ) {
            return null;
        }

        RequestSignUpDto.RequestSignUpDtoBuilder requestSignUpDto = RequestSignUpDto.builder();

        requestSignUpDto.idToken( e.getIdToken() );
        requestSignUpDto.oauthAccessToken( e.getOauthAccessToken() );
        requestSignUpDto.oauthRefreshToken( e.getOauthRefreshToken() );

        return requestSignUpDto.build();
    }

    @Override
    public RequestOAuthTokenDto toEntity(RequestSignUpDto d) {
        if ( d == null ) {
            return null;
        }

        RequestOAuthTokenDto requestOAuthTokenDto = new RequestOAuthTokenDto();

        requestOAuthTokenDto.setIdToken( d.getIdToken() );
        requestOAuthTokenDto.setOauthAccessToken( d.getOauthAccessToken() );
        requestOAuthTokenDto.setOauthRefreshToken( d.getOauthRefreshToken() );

        return requestOAuthTokenDto;
    }
}
