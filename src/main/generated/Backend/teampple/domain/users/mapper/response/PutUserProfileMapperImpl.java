package Backend.teampple.domain.users.mapper.response;

import Backend.teampple.domain.users.dto.request.PutUserProfileDto;
import Backend.teampple.domain.users.entity.UserProfile;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-01-10T23:44:26+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 11.0.15 (OpenLogic)"
)
@Component
public class PutUserProfileMapperImpl implements PutUserProfileMapper {

    @Override
    public Object toDto(Object e) {
        if ( e == null ) {
            return null;
        }

        Object object = new Object();

        return object;
    }

    @Override
    public Object toEntity(Object d) {
        if ( d == null ) {
            return null;
        }

        Object object = new Object();

        return object;
    }

    @Override
    public void updateFromDto(PutUserProfileDto d, UserProfile e) {
        if ( d == null ) {
            return;
        }

        if ( d.getSchoolName() != null ) {
            e.setSchoolName( d.getSchoolName() );
        }
        if ( d.getMajor() != null ) {
            e.setMajor( d.getMajor() );
        }
        if ( d.getEntranceYear() != null ) {
            e.setEntranceYear( d.getEntranceYear() );
        }
    }
}
