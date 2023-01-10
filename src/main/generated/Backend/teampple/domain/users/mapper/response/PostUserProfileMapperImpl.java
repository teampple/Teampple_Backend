package Backend.teampple.domain.users.mapper.response;

import Backend.teampple.domain.users.dto.request.PostUserProfileDto;
import Backend.teampple.domain.users.entity.UserProfile;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-01-10T22:33:18+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 11.0.15 (OpenLogic)"
)
@Component
public class PostUserProfileMapperImpl implements PostUserProfileMapper {

    @Override
    public PostUserProfileDto toDto(UserProfile e) {
        if ( e == null ) {
            return null;
        }

        PostUserProfileDto.PostUserProfileDtoBuilder postUserProfileDto = PostUserProfileDto.builder();

        postUserProfileDto.name( e.getName() );
        postUserProfileDto.schoolName( e.getSchoolName() );
        postUserProfileDto.major( e.getMajor() );

        return postUserProfileDto.build();
    }

    @Override
    public UserProfile toEntity(PostUserProfileDto d) {
        if ( d == null ) {
            return null;
        }

        UserProfile.UserProfileBuilder userProfile = UserProfile.builder();

        userProfile.name( d.getName() );
        userProfile.schoolName( d.getSchoolName() );
        userProfile.major( d.getMajor() );

        return userProfile.build();
    }
}
