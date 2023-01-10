package Backend.teampple.domain.files;

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

@RestController
@RequiredArgsConstructor
@Slf4j
@Api(tags = "파일")
@RequestMapping("/files")
public class FilesController {

    private final FilesService filesService;

    @GetMapping(value = "")
    @Operation(summary = "단계 조회", description = "단계 조회 API 입니다.\n"
            + "단계를 조회합니다.")
    public CommonResponse<List<GetFileDto>> getFile(@RequestParam("teamId") Long teamId) {
        log.info("[api-get] 단계 조회");

        // 유저 validation 추가해야함

        List<GetFileDto> fileDtos = filesService.getFile(teamId);
        return CommonResponse.onSuccess(HttpStatus.OK.value(), fileDtos);
    }
}
