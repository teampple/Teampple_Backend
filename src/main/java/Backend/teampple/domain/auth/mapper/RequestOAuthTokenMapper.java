package Backend.teampple.domain.auth.mapper;

import Backend.teampple.domain.auth.dto.RequestSignUpDto;
import Backend.teampple.domain.auth.dto.request.RequestOAuthTokenDto;
import Backend.teampple.global.common.mapper.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostOAuthTokenMapper extends GenericMapper<RequestSignUpDto, RequestOAuthTokenDto> {
}