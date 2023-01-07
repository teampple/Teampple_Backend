package Backend.teampple.domain.users.mapper;

import Backend.teampple.domain.users.dto.responseDto.UserProfileResponseDto;
import Backend.teampple.domain.users.dto.responseDto.UserProfileResponseDto.UserProfileResponseDtoBuilder;
import Backend.teampple.domain.users.entity.SubscriptionType;
import Backend.teampple.domain.users.entity.UserProfile;
import Backend.teampple.domain.users.entity.UserProfile.UserProfileBuilder;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-01-07T19:11:56+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.15 (OpenLogic)"
)
@Component
public class UserProfileMapperImpl implements UserProfileMapper {

    @Override
    public UserProfileResponseDto toDto(UserProfile e) {
        if ( e == null ) {
            return null;
        }

        UserProfileResponseDtoBuilder userProfileResponseDto = UserProfileResponseDto.builder();

        userProfileResponseDto.name( e.getName() );
        userProfileResponseDto.email( e.getEmail() );
        userProfileResponseDto.profileImage( e.getProfileImage() );
        userProfileResponseDto.schoolName( e.getSchoolName() );
        userProfileResponseDto.major( e.getMajor() );
        userProfileResponseDto.entranceYear( e.getEntranceYear() );
        if ( e.getSubscribePlan() != null ) {
            userProfileResponseDto.subscribePlan( e.getSubscribePlan().name() );
        }

        return userProfileResponseDto.build();
    }

    @Override
    public UserProfile toEntity(UserProfileResponseDto d) {
        if ( d == null ) {
            return null;
        }

        UserProfileBuilder userProfile = UserProfile.builder();

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

    @Override
    public void updateFromDto(UserProfileResponseDto dto, UserProfile entity) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getName() != null ) {
            entity.setName( dto.getName() );
        }
        if ( dto.getEmail() != null ) {
            entity.setEmail( dto.getEmail() );
        }
        if ( dto.getProfileImage() != null ) {
            entity.setProfileImage( dto.getProfileImage() );
        }
        if ( dto.getSchoolName() != null ) {
            entity.setSchoolName( dto.getSchoolName() );
        }
        if ( dto.getMajor() != null ) {
            entity.setMajor( dto.getMajor() );
        }
        if ( dto.getEntranceYear() != null ) {
            entity.setEntranceYear( dto.getEntranceYear() );
        }
        if ( dto.getSubscribePlan() != null ) {
            entity.setSubscribePlan( Enum.valueOf( SubscriptionType.class, dto.getSubscribePlan() ) );
        }
    }
}
