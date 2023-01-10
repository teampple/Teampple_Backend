package Backend.teampple.domain.files;

import Backend.teampple.domain.files.dto.request.PostFileDto;
import Backend.teampple.domain.files.dto.response.GetFileBriefDto;
import Backend.teampple.domain.files.dto.response.GetFileDto;
import Backend.teampple.domain.files.entity.File;
import Backend.teampple.domain.files.repository.FilesRepository;
import Backend.teampple.domain.tasks.entity.Task;
import Backend.teampple.domain.tasks.repository.TasksRepository;
import Backend.teampple.domain.teams.entity.Team;
import Backend.teampple.domain.teams.repository.TeamsRepository;
import Backend.teampple.domain.users.entity.User;
import Backend.teampple.global.error.ErrorCode;
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

    @Transactional
    public List<GetFileDto> getFile(Long teamId) {
        // 1. team 조회
        Team team = teamsRepository.findById(teamId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.TEAM_NOT_FOUND.getMessage()));

        // 2. file 조회
        List<File> files = filesRepository.findAllWithTeamAndUserByTeam(team);

        return files.stream()
                .map(o -> new GetFileDto(o))
                .collect(Collectors.toList());
    }

    @Transactional
    public GetFileBriefDto getFileBrief(Long teamId) {
        // 1. team 조회
        Team team = teamsRepository.findById(teamId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.TEAM_NOT_FOUND.getMessage()));

        // 2. file 조회
        List<File> files = filesRepository.findAllByTeam(team);

        // 3. dto 생성
        int totalSize = 0;
        int fileNum = files.size();
        for (int i = 0; i < files.size(); i++) {
            totalSize += files.get(i).getSize();
        }
        return GetFileBriefDto.builder()
                .fileNum(fileNum)
                .totalSize(totalSize)
                .build();
    }

    @Transactional
    public void postFile(PostFileDto postFileDto, Long taskId, Long teamId) {
        // 1. team 조회
        Team team = teamsRepository.findById(teamId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.TEAM_NOT_FOUND.getMessage()));

        // 2. task 조회
        Task task = tasksRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.TASK_NOT_FOUND.getMessage()));

        // 3. file 생성
        File file = File.builder()
                .url(postFileDto.getUrl())
                .filename(postFileDto.getFileName())
                .size(postFileDto.getSize())
                .task(task)
                .team(team)
//                .user()
                .build();
        filesRepository.save(file);
    }

    @Transactional
    public void deleteFile(Long fileId) {
        filesRepository.deleteById(fileId);
    }
}
