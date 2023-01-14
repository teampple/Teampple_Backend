package Backend.teampple.domain.templates;

import Backend.teampple.domain.templates.dto.request.PostBookmarkDto;
import Backend.teampple.domain.templates.entity.Bookmark;
import Backend.teampple.domain.templates.entity.Template;
import Backend.teampple.domain.templates.repository.BookmarkRepository;
import Backend.teampple.domain.templates.repository.TemplateRespository;
import Backend.teampple.global.error.ErrorCode;
import Backend.teampple.global.error.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final TemplateRespository templateRespository;

    private final BookmarkRepository bookmarkRepository;

    @Transactional
    public void postBookmark(PostBookmarkDto postBookmarkDto) {
        // 1. template 조회
        Template template = templateRespository.findById(postBookmarkDto.getId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.TEMPLATE_NOT_FOUND.getMessage()));

        // 2. bookmark 생성
//        Bookmark bookmark = Bookmark.builder()
//                .user()
//                .template(template)
//                .build();
//        bookmarkRepository.save(bookmark);
    }
}
