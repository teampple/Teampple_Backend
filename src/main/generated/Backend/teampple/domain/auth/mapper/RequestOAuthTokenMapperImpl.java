package Backend.teampple.domain.auth.mapper;

import Backend.teampple.domain.auth.dto.request.RequestOAuthTokenDto;
import Backend.teampple.domain.auth.dto.request.RequestSignUpDto;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-01-24T17:42:19+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 11.0.15 (OpenLogic)"
)
@Component
public class RequestOAuthTokenMapperImpl implements RequestOAuthTokenMapper {

    @Override
    public RequestSignUpDto toDto(RequestOAuthTokenDto arg0) {
        if ( arg0 == null ) {
            return null;
        }

        RequestSignUpDto.RequestSignUpDtoBuilder requestSignUpDto = RequestSignUpDto.builder();

        requestSignUpDto.idToken( arg0.getIdToken() );
        requestSignUpDto.oauthAccessToken( arg0.getOauthAccessToken() );
        requestSignUpDto.oauthRefreshToken( arg0.getOauthRefreshToken() );

        return requestSignUpDto.build();
    }

    @Override
    public RequestOAuthTokenDto toEntity(RequestSignUpDto arg0) {
        if ( arg0 == null ) {
            return null;
        }

        RequestOAuthTokenDto requestOAuthTokenDto = new RequestOAuthTokenDto();

        requestOAuthTokenDto.setIdToken( arg0.getIdToken() );
        requestOAuthTokenDto.setOauthAccessToken( arg0.getOauthAccessToken() );
        requestOAuthTokenDto.setOauthRefreshToken( arg0.getOauthRefreshToken() );

        return requestOAuthTokenDto;
    }
}
