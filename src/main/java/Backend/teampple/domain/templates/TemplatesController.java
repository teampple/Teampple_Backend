package Backend.teampple.domain.templates;

import Backend.teampple.domain.templates.dto.request.PostBookmarkDto;
import Backend.teampple.domain.templates.dto.response.GetTemplateDto;
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
@RequestMapping("/templates")
@Api(tags = "탬플릿")
public class TemplatesController {
    private final TemplatesService templatesService;

    private final BookmarkService bookmarkService;

    @GetMapping(value = "")
    @Operation(summary = "탬플릿 조회", description = "탬플릿 조회 API 입니다.")
    public List<GetTemplateDto> getTemplate(@AuthUser User authUser) {
        log.info("[api-get] 탬플릿 조회");
        log.info("{}", authUser);

        return templatesService.getTemplate(authUser);
    }

    @PostMapping(value = "bookmarks")
    @Operation(summary = "탬플릿 북마크 여부 변경", description = "탬플릿 북마크 여부 변경 API 입니다.")
    public void postBookmark(@AuthUser User authUser,
                                               @Valid @RequestBody PostBookmarkDto postBookmarkDto) {
        log.info("[api-post] 탬플릿 즐겨찾기");
        log.info("{}", authUser);

        bookmarkService.postBookmark(authUser, postBookmarkDto);
    }
}
