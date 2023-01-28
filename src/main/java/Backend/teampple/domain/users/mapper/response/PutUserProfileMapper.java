package Backend.teampple.domain.users.mapper.response;

import Backend.teampple.domain.users.dto.request.PutUserProfileDto;
import Backend.teampple.domain.users.entity.UserProfile;
import Backend.teampple.global.common.mapper.GenericUpdateMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PutUserProfileMapper extends GenericUpdateMapper<PutUserProfileDto, UserProfile> {
}
