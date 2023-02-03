package Backend.teampple.domain.files;

import Backend.teampple.domain.files.dto.request.PostFileDto;
import Backend.teampple.domain.files.dto.response.GetFileBriefDto;
import Backend.teampple.domain.files.dto.response.GetFileDto;
import Backend.teampple.domain.files.entity.File;
import Backend.teampple.domain.files.repository.FilesRepository;
import Backend.teampple.domain.tasks.entity.Task;
import Backend.teampple.domain.tasks.repository.TasksRepository;
import Backend.teampple.domain.users.entity.User;
import Backend.teampple.global.common.validation.dto.UserTeamDto;
import Backend.teampple.domain.teams.entity.Team;
import Backend.teampple.domain.teams.repository.TeammateRepository;
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
    private final FilesRepository filesRepository;

    private final TasksRepository tasksRepository;

    private final TeammateRepository teammateRepository;

    private final CheckUser checkUser;

    @Transactional
    public List<GetFileDto> getFile(User authUser, Long teamId) {
        // 1. 유저 체크 및 team 정보 불러오기
        Team team = checkUser.checkIsUserInTeamId(authUser, teamId);

        // 2. file 조회
        List<File> files = filesRepository.findAllByTeamWithTaskAndUserAndUserProfile(team);

        return files.stream()
                .map(GetFileDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void postFile(User authUser, PostFileDto postFileDto, Long taskId, Long teamId) {
        // 1. 유저 체크 및 team 정보 불러오기
        Team team = checkUser.checkIsUserInTeamId(authUser, teamId);

        // 2. task 조회
        Task task = tasksRepository.findByIdWithStage(taskId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.TASK_NOT_FOUND.getMessage()));

        // 3. team에 속하는 task 이어야 함
        if(task.getStage().getTeam() != team)
            throw new NotFoundException(ErrorCode.MISMATCH_TASK.getMessage());

        // 4. file 생성
        File file = File.builder()
                .url(postFileDto.getUrl())
                .filename(postFileDto.getFileName())
                .size(postFileDto.getSize())
                .task(task)
                .team(team)
                .user(authUser)
                .build();
        filesRepository.save(file);
    }

    @Transactional
    public void deleteFile(User authUser, Long fileId) {
        // 1. file 조회
        File file = filesRepository.findById(fileId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.FILE_NOT_FOUND.getMessage()));

        // 2. 유저 체크
        checkUser.checkIsUserInTeam(authUser,file.getTeam());

        filesRepository.delete(file);
    }

    @Transactional
    public GetFileBriefDto getFileBrief(User authUser, Long teamId) {
        // 1. 유저 체크 및 team 정보 불러오기
        Team team = checkUser.checkIsUserInTeamId(authUser, teamId);

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
