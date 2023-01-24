package Backend.teampple.domain.users.mapper.request;

import Backend.teampple.domain.users.dto.response.GetUserProfileDto;
import Backend.teampple.domain.users.entity.SubscriptionType;
import Backend.teampple.domain.users.entity.UserProfile;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-01-24T17:42:19+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 11.0.15 (OpenLogic)"
)
@Component
public class GetUserProfileMapperImpl implements GetUserProfileMapper {

    @Override
    public GetUserProfileDto toDto(UserProfile arg0) {
        if ( arg0 == null ) {
            return null;
        }

        GetUserProfileDto.GetUserProfileDtoBuilder getUserProfileDto = GetUserProfileDto.builder();

        getUserProfileDto.name( arg0.getName() );
        getUserProfileDto.email( arg0.getEmail() );
        getUserProfileDto.profileImage( arg0.getProfileImage() );
        getUserProfileDto.schoolName( arg0.getSchoolName() );
        getUserProfileDto.major( arg0.getMajor() );
        getUserProfileDto.entranceYear( arg0.getEntranceYear() );
        if ( arg0.getSubscribePlan() != null ) {
            getUserProfileDto.subscribePlan( arg0.getSubscribePlan().name() );
        }

        return getUserProfileDto.build();
    }

    @Override
    public UserProfile toEntity(GetUserProfileDto arg0) {
        if ( arg0 == null ) {
            return null;
        }

        UserProfile.UserProfileBuilder userProfile = UserProfile.builder();

        userProfile.name( arg0.getName() );
        userProfile.email( arg0.getEmail() );
        userProfile.profileImage( arg0.getProfileImage() );
        userProfile.schoolName( arg0.getSchoolName() );
        userProfile.major( arg0.getMajor() );
        userProfile.entranceYear( arg0.getEntranceYear() );
        if ( arg0.getSubscribePlan() != null ) {
            userProfile.subscribePlan( Enum.valueOf( SubscriptionType.class, arg0.getSubscribePlan() ) );
        }

        return userProfile.build();
    }
}
