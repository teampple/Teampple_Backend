package Backend.teampple.domain.files;

import Backend.teampple.domain.files.dto.request.PostFileDto;
import Backend.teampple.domain.files.dto.response.GetFileBriefDto;
import Backend.teampple.domain.files.dto.response.GetFileDto;
import Backend.teampple.domain.users.entity.User;
import Backend.teampple.global.common.auth.AuthUser;
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
    @Operation(summary = "파일 목록 조회", description = "파일 목록 조회 API 입니다.")
    public CommonResponse<List<GetFileDto>> getFile(@AuthUser User authUser,
                                                    @RequestParam("teamId") Long teamId) {
        log.info("[api-get] 파일 목록 조회");
        log.info("{}", authUser);

        List<GetFileDto> fileDtos = filesService.getFile(authUser, teamId);
        return CommonResponse.onSuccess(HttpStatus.OK.value(), fileDtos);
    }

    @PostMapping(value = "")
    @Operation(summary = "파일 등록", description = "파일 등록 API 입니다.")
    public CommonResponse<String> postFile(@AuthUser User authUser,
                                           @Valid @RequestBody PostFileDto postFileDto,
                                           @RequestParam("taskId") Long taskId,
                                           @RequestParam("teamId") Long teamId) {
        log.info("[api-post] 파일 등록");
        log.info("{}", authUser);

        filesService.postFile(authUser, postFileDto, taskId, teamId);
        return CommonResponse.onSuccess(HttpStatus.CREATED.value());
    }

    @DeleteMapping(value = "")
    @Operation(summary = "파일 삭제", description = "파일 삭제 API 입니다.")
    public CommonResponse<String> deleteFile(@AuthUser User authUser,
                                             @RequestParam("fileId") Long fileId) {
        log.info("[api-delete] 파일 삭제");
        log.info("{}", authUser);

        filesService.deleteFile(authUser, fileId);
        return CommonResponse.onSuccess(HttpStatus.NO_CONTENT.value());
    }

    @GetMapping(value = "info")
    @Operation(summary = "파일 갯수 조회", description = "파일 갯수 조회 API 입니다.")
    public CommonResponse<GetFileBriefDto> getFileBrief(@AuthUser User authUser,
                                                        @RequestParam("teamId") Long teamId) {
        log.info("[api-get] 파일 갯수 조회");
        log.info("{}", authUser);

        GetFileBriefDto fileBriefDto = filesService.getFileBrief(authUser, teamId);
        return CommonResponse.onSuccess(HttpStatus.OK.value(), fileBriefDto);
    }
}
