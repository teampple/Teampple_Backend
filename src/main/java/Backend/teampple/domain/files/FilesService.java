package Backend.teampple.domain.files;

import Backend.teampple.domain.files.dto.response.GetFileDto;
import Backend.teampple.domain.files.entity.File;
import Backend.teampple.domain.files.repository.FilesRepository;
import Backend.teampple.domain.teams.entity.Team;
import Backend.teampple.domain.teams.repository.TeamsRepository;
import Backend.teampple.global.error.ErrorCode;
import Backend.teampple.global.error.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilesService {

    private final TeamsRepository teamsRepository;

    private final FilesRepository filesRepository;

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
}
