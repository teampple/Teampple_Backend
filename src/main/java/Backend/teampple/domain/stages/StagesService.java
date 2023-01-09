package Backend.teampple.domain.stages;

import Backend.teampple.domain.stages.dto.StageDto;
import Backend.teampple.domain.stages.entity.Stage;
import Backend.teampple.domain.teams.TeamsRepository;
import Backend.teampple.domain.teams.entity.Team;
import Backend.teampple.global.error.ErrorCode;
import Backend.teampple.global.error.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class StagesService {

    private final TeamsRepository teamsRepository;

    private final StagesRepository stagesRepository;

    @Transactional
    public List<StageDto> getStage(Long teamId) {
        // 1. team 조회
        Team team = teamsRepository.findById(teamId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.TEAM_NOT_FOUND.getMessage()));

        // 2. stages 조회
        List<Stage> stages = stagesRepository.findAllByTeamOrderBySequenceNum(team);
        List<StageDto> stageDtos = stages.stream()
                .map(stage -> new StageDto(stage))
                .collect(Collectors.toList());

        return stageDtos;
    }

    @Transactional
    public void postStage(StageDto stageDto, Long teamId) {
        // 1. team 조회
        Team team = teamsRepository.findById(teamId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.TEAM_NOT_FOUND.getMessage()));

        // 2. stage sequenceNum 검사
        if (stagesRepository.findAllByTeamAndSequenceNum(team, stageDto.getSequenceNum()).isPresent()) {
            throw new NotFoundException(ErrorCode.STAGE_SEQUENCE_DUPLICATE.getMessage());
        }

        // 3. stage 생성
        Stage stage = Stage.builder()
                .team(team)
                .taskName(stageDto.getName())
                .sequenceNum(stageDto.getSequenceNum())
                .startDate(stageDto.getStartDate())
                .dueDate(stageDto.getDueDate())
                .isDone(false)
                .build();
        stagesRepository.save(stage);
    }

    @Transactional
    public void putStage(List<StageDto> stagesDto, Long teamId) {
        // 1. team 조회
        Team team = teamsRepository.findById(teamId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.TEAM_NOT_FOUND.getMessage()));

        // 2. stage 조회
        List<Stage> stages = stagesRepository.findAllByTeamOrderBySequenceNum(team);

        // 3. stage 업데이트
        ListIterator<StageDto> stageDtoIt = stagesDto.listIterator();
        ListIterator<Stage> stageIt = stages.listIterator();
        while (stageIt.hasNext()) {
            if (stageDtoIt.hasNext()) { // 기존에 존재하던거 업데이트
                stageIt.next().update(stageDtoIt.next());
            } else { // 기존보다 수정본이 짧으면 삭제
                stagesRepository.delete(stageIt.next());
            }
        }
        while (stageDtoIt.hasNext()) { // 길면 추가
            StageDto temp = stageDtoIt.next();
            Stage stage = Stage.builder()
                    .team(team)
                    .taskName(temp.getName())
                    .sequenceNum(temp.getSequenceNum())
                    .startDate(temp.getStartDate())
                    .dueDate(temp.getDueDate())
                    .isDone(false)
                    .build();
            stagesRepository.save(stage);
        }
    }
}
