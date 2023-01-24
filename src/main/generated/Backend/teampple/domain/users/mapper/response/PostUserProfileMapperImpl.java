package Backend.teampple.domain.users.mapper.response;

import Backend.teampple.domain.auth.dto.request.RequestSignUpDto;
import Backend.teampple.domain.users.entity.UserProfile;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-01-24T17:42:19+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 11.0.15 (OpenLogic)"
)
@Component
public class PostUserProfileMapperImpl implements PostUserProfileMapper {

    @Override
    public RequestSignUpDto toDto(UserProfile arg0) {
        if ( arg0 == null ) {
            return null;
        }

        RequestSignUpDto.RequestSignUpDtoBuilder requestSignUpDto = RequestSignUpDto.builder();

        requestSignUpDto.name( arg0.getName() );
        requestSignUpDto.profileImage( arg0.getProfileImage() );
        requestSignUpDto.schoolName( arg0.getSchoolName() );
        requestSignUpDto.major( arg0.getMajor() );

        return requestSignUpDto.build();
    }

    @Override
    public UserProfile toEntity(RequestSignUpDto arg0) {
        if ( arg0 == null ) {
            return null;
        }

        UserProfile.UserProfileBuilder userProfile = UserProfile.builder();

        userProfile.name( arg0.getName() );
        userProfile.profileImage( arg0.getProfileImage() );
        userProfile.schoolName( arg0.getSchoolName() );
        userProfile.major( arg0.getMajor() );

        return userProfile.build();
    }
}
