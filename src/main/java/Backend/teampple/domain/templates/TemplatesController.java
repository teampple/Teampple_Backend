package Backend.teampple.domain.templates;

import Backend.teampple.domain.templates.dto.request.PostBookmarkDto;
import Backend.teampple.domain.templates.dto.response.GetTemplateDto;
import Backend.teampple.global.common.response.CommonResponse;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/templates")
@Api(tags = "탬플릿")
public class TemplatesController {
    private final TemplatesService templatesService;

    private final BookmarkService bookmarkService;

    @GetMapping(value = "")
    @Operation(summary = "탬플릿 조회", description = "탬플릿 조회 API 입니다.")
    public CommonResponse<List<GetTemplateDto>> getTemplate(@AuthenticationPrincipal String authUser) {
        log.info("[api-get] 탬플릿 조회");

        List<GetTemplateDto> getTemplateDtos = templatesService.getTemplate(authUser);
        return CommonResponse.onSuccess(HttpStatus.OK.value(), getTemplateDtos);
    }

    @PostMapping(value = "bookmarks")
    @Operation(summary = "탬플릿 북마크", description = "탬플릿 북마크 API 입니다.\n"
            + "탬플릿 즐겨찾기합니다. 작동x")
    public CommonResponse<String> postBookmark(@AuthenticationPrincipal String authUser,
                                               @Valid @RequestBody PostBookmarkDto postBookmarkDto) {
        log.info("[api-post] 탬플릿 즐겨찾기");

        bookmarkService.postBookmark(authUser, postBookmarkDto);
        return CommonResponse.onSuccess(HttpStatus.CREATED.value());
    }
}
