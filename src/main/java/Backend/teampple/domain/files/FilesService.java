package Backend.teampple.domain.files;

import Backend.teampple.domain.files.dto.request.PostFileDto;
import Backend.teampple.domain.files.dto.response.GetFileBriefDto;
import Backend.teampple.domain.files.dto.response.GetFileDto;
import Backend.teampple.domain.files.entity.File;
import Backend.teampple.domain.files.repository.FilesRepository;
import Backend.teampple.domain.tasks.entity.Task;
import Backend.teampple.domain.tasks.repository.TasksRepository;
import Backend.teampple.domain.teams.dto.UserTeamDto;
import Backend.teampple.domain.teams.entity.Team;
import Backend.teampple.domain.teams.repository.TeammateRepository;
import Backend.teampple.domain.teams.repository.TeamsRepository;
import Backend.teampple.global.common.validation.CheckUser;
import Backend.teampple.global.error.ErrorCode;
import Backend.teampple.global.error.exception.ForbiddenException;
import Backend.teampple.global.error.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilesService {

    private final TeamsRepository teamsRepository;

    private final FilesRepository filesRepository;

    private final TasksRepository tasksRepository;

    private final TeammateRepository teammateRepository;

    private final CheckUser checkUser;

    @Transactional
    public List<GetFileDto> getFile(String authUser, Long teamId) {
        // 1. 유저 체크 및 team 정보 불러오기
        Team team = checkUser.checkIsUserInTeam(authUser, teamId).getTeam();

        // 2. file 조회
        List<File> files = filesRepository.findAllWithTeamAndUserByTeam(team);

        return files.stream()
                .map(GetFileDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void postFile(String authUser, PostFileDto postFileDto, Long taskId, Long teamId) {
        // 1. 유저 체크 및 team 정보 불러오기
        UserTeamDto userTeamDto = checkUser.checkIsUserInTeam(authUser, teamId);

        // 2. task 조회
        Task task = tasksRepository.findByIdWithStageAndTeam(taskId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.TASK_NOT_FOUND.getMessage()));

        // 3. team에 속하는 task 이어야 함
        if(task.getStage().getTeam() != userTeamDto.getTeam())
            throw new NotFoundException(ErrorCode._BAD_REQUEST.getMessage());

        // 4. file 생성
        File file = File.builder()
                .url(postFileDto.getUrl())
                .filename(postFileDto.getFileName())
                .size(postFileDto.getSize())
                .task(task)
                .team(userTeamDto.getTeam())
                .user(userTeamDto.getUser())
                .build();
        filesRepository.save(file);
    }

    @Transactional
    public void deleteFile(String authUser, Long fileId) {
        // 1. file로 팀까지
        File file = filesRepository.findByIdWithTeam(fileId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.TASK_NOT_FOUND.getMessage()));

        // 2. 팀메이트에서 유저까지 해서 없으면 에러
        teammateRepository.findByUserIdAndTeam(authUser, file.getTeam())
                .orElseThrow(() -> new ForbiddenException(ErrorCode.FORBIDDEN_USER.getMessage()));

        filesRepository.delete(file);
    }

    @Transactional
    public GetFileBriefDto getFileBrief(String authUser, Long teamId) {
        // 1. 유저 체크 및 team 정보 불러오기
        Team team = checkUser.checkIsUserInTeam(authUser, teamId).getTeam();

        // 2. file 조회
        List<File> files = filesRepository.findAllByTeam(team);

        // 3. dto 생성
        int totalSize = 0;
        for (File file : files) {
            totalSize += file.getSize();
        }
        return GetFileBriefDto.builder()
                .fileNum(files.size())
                .totalSize(totalSize)
                .build();
    }
}
