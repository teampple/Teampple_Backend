package com.company.users.mapper.response;

import Backend.teampple.domain.users.dto.request.PutUserProfileDto;
import Backend.teampple.domain.users.entity.UserProfile;
import Backend.teampple.global.common.mapper.GenericUpdateMapper;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PutUserProfileMapper extends GenericUpdateMapper<PutUserProfileDto, UserProfile> {
}
