package Backend.teampple.global.common.validation;

import Backend.teampple.domain.teams.dto.UserTeammateDto;
import Backend.teampple.domain.teams.entity.Team;
import Backend.teampple.domain.teams.entity.Teammate;
import Backend.teampple.domain.teams.repository.TeammateRepository;
import Backend.teampple.domain.users.entity.User;
import Backend.teampple.domain.users.repository.UserRepository;
import Backend.teampple.global.error.ErrorCode;
import Backend.teampple.global.error.exception.BadRequestException;
import Backend.teampple.global.error.exception.ForbiddenException;
import Backend.teampple.global.error.exception.NotFoundException;
import Backend.teampple.global.error.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
@RequiredArgsConstructor
public class CheckUser {
    private final TeammateRepository teammateRepository;

    private final UserRepository userRepository;

    /*
    해당 유저가 팀에 속한지 검사하는 method
    */
    public UserTeammateDto checkIsUserInTeam(String authUser, Team team) {
        // 1. teammate 정보 불러오기
        List<Teammate> teammates = teammateRepository.findAllByTeamWithUser(team);
        if(teammates.isEmpty())
            throw new BadRequestException(ErrorCode.TEAM_NOT_VALID.getMessage());

        // 2. 유저 불러오기
        User user = userRepository.findByKakaoIdWithUserProfile(authUser)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND.getMessage()));

        // 3. 유저 확인
        List<User> users = teammates.stream().map(teammate -> teammate.getUser()).collect(toList());
        if(!users.contains(user))
            throw new ForbiddenException(ErrorCode.FORBIDDEN_USER.getMessage());

        return UserTeammateDto.builder()
                .user(user)
                .teammate(teammates)
                .build();
    }
}
