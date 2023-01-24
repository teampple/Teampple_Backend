package Backend.teampple.domain.users.mapper.response;

import Backend.teampple.domain.auth.dto.request.RequestSignUpDto;
import Backend.teampple.domain.users.entity.UserProfile;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-01-24T19:56:10+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 11.0.15 (OpenLogic)"
)
@Component
public class PostUserProfileMapperImpl implements PostUserProfileMapper {

    @Override
    public RequestSignUpDto toDto(UserProfile e) {
        if ( e == null ) {
            return null;
        }

        RequestSignUpDto.RequestSignUpDtoBuilder requestSignUpDto = RequestSignUpDto.builder();

        requestSignUpDto.name( e.getName() );
        requestSignUpDto.profileImage( e.getProfileImage() );
        requestSignUpDto.schoolName( e.getSchoolName() );
        requestSignUpDto.major( e.getMajor() );

        return requestSignUpDto.build();
    }

    @Override
    public UserProfile toEntity(RequestSignUpDto d) {
        if ( d == null ) {
            return null;
        }

        UserProfile.UserProfileBuilder userProfile = UserProfile.builder();

        userProfile.name( d.getName() );
        userProfile.profileImage( d.getProfileImage() );
        userProfile.schoolName( d.getSchoolName() );
        userProfile.major( d.getMajor() );

        return userProfile.build();
    }
}
