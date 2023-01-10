package com.company.users.mapper.response;

import Backend.teampple.domain.users.dto.request.PostUserProfileDto;
import Backend.teampple.domain.users.entity.UserProfile;
import Backend.teampple.global.common.mapper.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostUserProfileMapper extends GenericMapper<PostUserProfileDto, UserProfile> {
}
