package Backend.teampple.domain.teams;

import Backend.teampple.domain.stages.StagesRepository;
import Backend.teampple.domain.stages.dto.request.PostStageDto;
import Backend.teampple.domain.stages.entity.Stage;
import Backend.teampple.domain.teams.dto.request.PostTeamDto;
import Backend.teampple.domain.teams.dto.response.GetTeamDetailDto;
import Backend.teampple.domain.teams.entity.Team;
import Backend.teampple.domain.teams.entity.Teammate;
import Backend.teampple.global.common.entity.PeriodBaseEntity;
import Backend.teampple.global.error.ErrorCode;
import Backend.teampple.global.error.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TeamsService{
    private final TeamsRepository teamsRepository;
    private final StagesRepository stagesRepository;
    private final TeammateRepository teammateRepository;

    public void createTeam(PostTeamDto postTeamDto) {

        // 1. 팀 생성
        Team team = Team.builder()
                .name(postTeamDto.getName())
                .goal(postTeamDto.getGoal())
                .startDate(postTeamDto.getStartDate())
                .dueDate(postTeamDto.getDueDate())
                .build();
        teamsRepository.save(team);

//        // 2. 팀 생성한 사람 팀원으로 추가
//        Teammate teammate = Teammate.builder()
//                .team(team)
//                .user(user)
//                .build();
//        teammateRepository.save(teammate);

        // 3. 단계 생성
        List<PostStageDto> stages = postTeamDto.getStages();

        stages.forEach(postStageDto ->
                {
                    Stage stage = Stage.builder()
                            .team(team)
                            .taskName(postStageDto.getName())
                            .sequenceNum(postStageDto.getSequenceNum())
                            .startDate(postStageDto.getStartDate())
                            .dueDate(postTeamDto.getDueDate())
                            .build();
                    stagesRepository.save(stage);
                }
        );
    }

    public GetTeamDetailDto getTeamDetail(Long teamId) {
        // 1. team 정보 불러오기
        Team team = teamsRepository.findById(teamId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.TEAM_NOT_FOUND.getMessage()));

        // 2. teammate 정보 불러오기
        List<Teammate> teammates = teammateRepository.findAllByTeam(team);

        // 3. 유저가 해당 팀에 속한지 확인


        // 4. DTO 생성
        return GetTeamDetailDto.builder()
                .name(team.getName())
                .goal(team.getGoal())
                .startDate(team.getStartDate())
                .dueDate(team.getDueDate())
                .teammates(teammates)
                .build();
    }
}
