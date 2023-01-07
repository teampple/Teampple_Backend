package Backend.teampple.domain.users.mapper;

import Backend.teampple.domain.users.dto.UserProfileDto;
import Backend.teampple.domain.users.entity.UserProfile;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-01-07T20:00:08+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 11.0.15 (OpenLogic)"
)
@Component
public class UserProfileMapperImpl implements UserProfileMapper {

    @Override
    public UserProfileDto toDto(UserProfile e) {
        if ( e == null ) {
            return null;
        }

        UserProfileDto.UserProfileDtoBuilder userProfileDto = UserProfileDto.builder();

        userProfileDto.name( e.getName() );
        userProfileDto.email( e.getEmail() );
        userProfileDto.profileImage( e.getProfileImage() );
        userProfileDto.schoolName( e.getSchoolName() );
        userProfileDto.major( e.getMajor() );
        userProfileDto.entranceYear( e.getEntranceYear() );
        if ( e.getSubscribePlan() != null ) {
            userProfileDto.subscribePlan( e.getSubscribePlan().name() );
        }

        return userProfileDto.build();
    }

    @Override
    public UserProfile toEntity(UserProfileDto d) {
        if ( d == null ) {
            return null;
        }

        UserProfile.UserProfileBuilder userProfile = UserProfile.builder();

        return userProfile.build();
    }

    @Override
    public void updateFromDto(UserProfileDto d, UserProfile entity) {
        if ( d == null ) {
            return;
        }
    }
}
