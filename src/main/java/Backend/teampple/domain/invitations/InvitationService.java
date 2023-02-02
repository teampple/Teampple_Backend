package Backend.teampple.domain.invitations;

import Backend.teampple.domain.invitations.dto.response.GetInvitationDto;
import Backend.teampple.domain.invitations.dto.response.GetInvitationValidationDto;
import Backend.teampple.domain.invitations.entity.Invitation;
import Backend.teampple.domain.invitations.repository.InvitationRepository;
import Backend.teampple.domain.teams.entity.Team;
import Backend.teampple.domain.teams.entity.Teammate;
import Backend.teampple.domain.teams.repository.TeammateRepository;
import Backend.teampple.domain.teams.repository.TeamsRepository;
import Backend.teampple.domain.users.entity.User;
import Backend.teampple.domain.users.repository.UserRepository;
import Backend.teampple.global.common.validation.CheckUser;
import Backend.teampple.global.error.ErrorCode;
import Backend.teampple.global.error.exception.BadRequestException;
import Backend.teampple.global.error.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class InvitationService {
    private final TeamsRepository teamsRepository;

    private final InvitationRepository invitationRepository;

    private final TeammateRepository teammateRepository;

    private final UserRepository userRepository;

    private final CheckUser checkUser;

    @Transactional
    public GetInvitationDto getInvitation(User authUser, Long teamId, String referer, String host) {
        // 1. 유저 체크
        checkUser.checkIsUserInTeamId(authUser, teamId);

        // 2. 코드 생성
        String code = UUID.randomUUID().toString() // 10자리
                .replaceAll("-","")
                .substring(3,13);

        // 3. 레디스용 객체(invitation) 생성
        Invitation invitation = Invitation.builder()
                .teamId(teamId)
                .code(code)
                .build();

        // 4. 레디스 저장
        invitationRepository.save(invitation);

        // 5. 주소로 변환
        String link;
        if (referer.contains(host)) {
            // www.teampple.site
            // www.teampple.com
            link = String.format("https://%s/login/", host) + code;
        } else {
            // localhost:3000
            link = referer + code;
        }

        // 6. return
        return GetInvitationDto.builder()
                .url(link)
                .build();
    }

    public void postInvitation(User authUser, String code) {
        // 1. 초대장 찾기
        Invitation invite = invitationRepository.findByCode(code);

        // 1.2 초대장 없으면 return
        if (invite == null) {
            throw new BadRequestException(ErrorCode.NOT_VALID_INVITATION.getMessage());
        }

        // 2. 팀 조회
        Team team = teamsRepository.findById(invite.getTeamId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.TEAM_NOT_FOUND.getMessage()));

        // 3. 이미 존재하는 팀원인지 체크
        List<Teammate> teammates = teammateRepository.findAllByTeamWithUser(team);
        teammates.forEach(teammate -> {
                    if(authUser.equals(teammate.getUser()))
                        throw new BadRequestException(ErrorCode.TEAMMATE_ALREADY_EXIST.getMessage());
                });

        // 3. 팀원 생성
        Teammate teammate = Teammate.builder()
                .user(authUser)
                .userProfile(authUser.getUserProfile())
                .team(team)
                .build();
        teammateRepository.save(teammate);
    }

    @Transactional
    public GetInvitationValidationDto getInvitationValidation(String code) {
        // 1. 초대장 찾기
        Invitation invite = invitationRepository.findByCode(code);

        // 2.1 초대장 없으면 return
        if (invite == null) {
            return GetInvitationValidationDto.builder()
                    .teamName("")
                    .isValid(false)
                    .build();
        }

        // 2.2 초대장 존재하면 팀 조회 후 return
        Team team = teamsRepository.findById(invite.getTeamId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.TASK_NOT_FOUND.getMessage()));
        return GetInvitationValidationDto.builder()
                .teamName(team.getName())
                .isValid(true)
                .build();
    }
}
