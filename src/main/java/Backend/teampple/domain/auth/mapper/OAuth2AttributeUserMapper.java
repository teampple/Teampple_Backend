package Backend.teampple.domain.auth.mapper;

import Backend.teampple.domain.auth.oauth.OAuth2Attribute;
import Backend.teampple.domain.users.entity.User;
import Backend.teampple.global.common.mapper.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OAuth2AttributeUserMapper extends GenericMapper<User, OAuth2Attribute> {
    @Override
    @Mapping(source = "OAuthId", target = "kakaoId")
    User toDto(OAuth2Attribute oAuth2Attribute);
}
