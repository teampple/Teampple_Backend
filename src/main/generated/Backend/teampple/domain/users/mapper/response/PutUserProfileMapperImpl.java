package Backend.teampple.domain.users.mapper.response;

import Backend.teampple.domain.users.dto.request.PutUserProfileDto;
import Backend.teampple.domain.users.entity.UserProfile;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-01-24T17:42:18+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 11.0.15 (OpenLogic)"
)
@Component
public class PutUserProfileMapperImpl implements PutUserProfileMapper {

    @Override
    public Object toDto(Object arg0) {
        if ( arg0 == null ) {
            return null;
        }

        Object object = new Object();

        return object;
    }

    @Override
    public Object toEntity(Object arg0) {
        if ( arg0 == null ) {
            return null;
        }

        Object object = new Object();

        return object;
    }

    @Override
    public void updateFromDto(PutUserProfileDto arg0, UserProfile arg1) {
        if ( arg0 == null ) {
            return;
        }

        if ( arg0.getSchoolName() != null ) {
            arg1.setSchoolName( arg0.getSchoolName() );
        }
        if ( arg0.getMajor() != null ) {
            arg1.setMajor( arg0.getMajor() );
        }
        if ( arg0.getEntranceYear() != null ) {
            arg1.setEntranceYear( arg0.getEntranceYear() );
        }
    }
}
