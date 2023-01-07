package Backend.teampple.domain.users.mapper;

import Backend.teampple.domain.users.dto.UserProfileDto;
import Backend.teampple.domain.users.entity.UserProfile;
import Backend.teampple.global.common.mapper.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserProfileMapper extends GenericMapper<UserProfileDto, UserProfile> {
}
