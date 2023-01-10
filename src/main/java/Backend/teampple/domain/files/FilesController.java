package Backend.teampple.domain.files;

import Backend.teampple.domain.files.dto.request.PostFileDto;
import Backend.teampple.domain.files.dto.response.GetFileBriefDto;
import Backend.teampple.domain.files.dto.response.GetFileDto;
import Backend.teampple.domain.files.dto.response.GetFileInfoDto;
import Backend.teampple.domain.files.repository.FilesRepository;
import Backend.teampple.domain.stages.dto.StageDto;
import Backend.teampple.global.common.response.CommonResponse;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/files")
@Api(tags = "파일")
public class FilesController {

    private final FilesService filesService;

    @GetMapping(value = "")
    @Operation(summary = "파일 목록 조회", description = "파일 목록 조회 API 입니다.\n"
            + "파일 목록을 조회합니다.")
    public CommonResponse<List<GetFileDto>> getFile(@RequestParam("teamId") Long teamId) {
        log.info("[api-get] 파일 목록 조회");

        // 유저 validation 추가해야함

        List<GetFileDto> fileDtos = filesService.getFile(teamId);
        return CommonResponse.onSuccess(HttpStatus.OK.value(), fileDtos);
    }

    @GetMapping(value = "/info")
    @Operation(summary = "파일 갯수 조회", description = "파일 갯수 조회 API 입니다.\n"
            + "파일 갯수를 조회합니다.")
    public CommonResponse<GetFileBriefDto> getFileBrief(@RequestParam("teamId") Long teamId) {
        log.info("[api-get] 파일 갯수 조회");

        // 유저 validation 추가해야함

        GetFileBriefDto fileBriefDto = filesService.getFileBrief(teamId);
        return CommonResponse.onSuccess(HttpStatus.OK.value(), fileBriefDto);
    }

    @PostMapping(value = "")
    @Operation(summary = "파일 등록", description = "파일 등록 API 입니다.\n"
            + "파일을 등록합니다.")
    public CommonResponse<String> postFile(@Valid @RequestBody PostFileDto postFileDto,
                                           @RequestParam("taskId") Long taskId,
                                           @RequestParam("teamId") Long teamId) {
        log.info("[api-post] 파일 등록");

        // 유저 validation 추가해야함

        // 유저도 넘겨서 file 만들 때 추가해야함
        filesService.postFile(postFileDto, taskId, teamId);
        return CommonResponse.onSuccess(HttpStatus.CREATED.value());
    }

    @DeleteMapping(value = "")
    @Operation(summary = "파일 삭제", description = "파일 삭제 API 입니다.\n"
            + "파일을 삭제합니다.")
    public CommonResponse<String> deleteFile(@RequestParam("fileId") Long fileId) {

        log.info("[api-delete] 파일 삭제");

        // 유저 validation 추가해야함

        // 유저도 넘겨서 file 만들 때 추가해야함
        filesService.deleteFile(fileId);
        return CommonResponse.onSuccess(HttpStatus.NO_CONTENT.value());
    }
}
