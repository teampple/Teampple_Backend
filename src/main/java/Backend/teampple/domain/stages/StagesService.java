package Backend.teampple.domain.stages;

import Backend.teampple.domain.stages.dto.StageDto;
import Backend.teampple.domain.stages.dto.request.PostStageDto;
import Backend.teampple.domain.stages.dto.request.PutStageDto;
import Backend.teampple.domain.stages.entity.Stage;
import Backend.teampple.domain.stages.repository.StagesRepository;
import Backend.teampple.domain.teams.repository.TeamsRepository;
import Backend.teampple.domain.teams.entity.Team;
import Backend.teampple.domain.users.entity.User;
import Backend.teampple.global.common.validation.CheckUser;
import Backend.teampple.global.error.ErrorCode;
import Backend.teampple.global.error.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class StagesService {
    private final StagesRepository stagesRepository;

    private final CheckUser checkUser;

    @Transactional
    public List<StageDto> getStage(User authUser, Long teamId) {
        // 1. 유저 체크 및 team 정보 불러오기
        Team team = checkUser.checkIsUserInTeamId(authUser, teamId);

        // 2. stages 조회
        List<Stage> stages = stagesRepository.findAllByTeamOrderBySequenceNum(team);

        return stages.stream()
                .map(StageDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void postStage(User authUser, PostStageDto postStageDto, Long teamId) {
        // 1. 유저 체크 및 team 정보 불러오기
        Team team = checkUser.checkIsUserInTeamId(authUser, teamId);

        // 2. stage sequenceNum 검사
        for (int i = 1; i<=postStageDto.getStages().size();i++) {
            if (postStageDto.getStages().get(i - 1).getSequenceNum() != i) {
                throw new BadRequestException(ErrorCode.STAGE_SEQUENCE_DUPLICATE.getMessage());
            }
        }

        // 3. stage 생성
        postStageDto.getStages()
                .forEach(stageDto ->
                {
                    Stage stage = Stage.builder()
                            .team(team)
                            .taskName(stageDto.getName())
                            .sequenceNum(stageDto.getSequenceNum())
                            .startDate(stageDto.getStartDate())
                            .dueDate(stageDto.getDueDate())
                            .isDone(false)
                            .build();
                    stagesRepository.save(stage);
                });
    }

    @Transactional
    public void putStage(User authUser, PutStageDto putStageDto, Long teamId) {
        // 1. 유저 체크 및 team 정보 불러오기
        Team team = checkUser.checkIsUserInTeamId(authUser, teamId);

        // 2. stage sequenceNum 검사
        for (int i = 1; i<=putStageDto.getStages().size();i++) {
            if (putStageDto.getStages().get(i - 1).getSequenceNum() != i) {
                throw new BadRequestException(ErrorCode.STAGE_SEQUENCE_DUPLICATE.getMessage());
            }
        }

        // 3. stage 조회
        List<Stage> stages = stagesRepository.findAllByTeamOrderBySequenceNum(team);

        // 4. stage 업데이트
        List<StageDto> stageDtos = putStageDto.getStages();
        ListIterator<StageDto> stageDtoIt = stageDtos.listIterator();
        ListIterator<Stage> stageIt = stages.listIterator();
        while (stageIt.hasNext()) {
            if (stageDtoIt.hasNext()) { // 기존에 존재하던거 업데이트
                Stage next = stageIt.next();
                next.update(stageDtoIt.next());
                stagesRepository.save(next);
            } else { // 기존보다 수정본이 짧으면 삭제
                stagesRepository.delete(stageIt.next());
            }
        }
        while (stageDtoIt.hasNext()) { // 길면 추가
            StageDto next = stageDtoIt.next();
            Stage stage = Stage.builder()
                    .team(team)
                    .taskName(next.getName())
                    .sequenceNum(next.getSequenceNum())
                    .startDate(next.getStartDate())
                    .dueDate(next.getDueDate())
                    .isDone(false)
                    .build();
            stagesRepository.save(stage);
        }
    }
}
