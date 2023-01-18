package Backend.teampple.domain.users.mapper.request;

import Backend.teampple.domain.users.dto.response.GetUserProfileDto;
import Backend.teampple.domain.users.entity.SubscriptionType;
import Backend.teampple.domain.users.entity.UserProfile;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-01-18T23:21:28+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 11.0.15 (OpenLogic)"
)
@Component
public class GetUserProfileMapperImpl implements GetUserProfileMapper {

    @Override
    public GetUserProfileDto toDto(UserProfile e) {
        if ( e == null ) {
            return null;
        }

        GetUserProfileDto.GetUserProfileDtoBuilder getUserProfileDto = GetUserProfileDto.builder();

        getUserProfileDto.name( e.getName() );
        getUserProfileDto.email( e.getEmail() );
        getUserProfileDto.profileImage( e.getProfileImage() );
        getUserProfileDto.schoolName( e.getSchoolName() );
        getUserProfileDto.major( e.getMajor() );
        getUserProfileDto.entranceYear( e.getEntranceYear() );
        if ( e.getSubscribePlan() != null ) {
            getUserProfileDto.subscribePlan( e.getSubscribePlan().name() );
        }

        return getUserProfileDto.build();
    }

    @Override
    public UserProfile toEntity(GetUserProfileDto d) {
        if ( d == null ) {
            return null;
        }

        UserProfile.UserProfileBuilder userProfile = UserProfile.builder();

        userProfile.name( d.getName() );
        userProfile.email( d.getEmail() );
        userProfile.profileImage( d.getProfileImage() );
        userProfile.schoolName( d.getSchoolName() );
        userProfile.major( d.getMajor() );
        userProfile.entranceYear( d.getEntranceYear() );
        if ( d.getSubscribePlan() != null ) {
            userProfile.subscribePlan( Enum.valueOf( SubscriptionType.class, d.getSubscribePlan() ) );
        }

        return userProfile.build();
    }
}
