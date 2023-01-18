package Backend.teampple.domain.users.mapper.response;

import Backend.teampple.domain.auth.dto.request.RequestSignUpDto;
import Backend.teampple.domain.users.entity.UserProfile;
import Backend.teampple.global.common.mapper.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostUserProfileMapper extends GenericMapper<RequestSignUpDto, UserProfile> {
//    TODO: 플랜 등록 가능 시.
//    @Override
//    @Mapping(target = "subscribePlan", source = "subscribePlan", defaultValue = "'FreePlan'")
//    UserProfile toEntity(RequestSignUpDto d);
}
