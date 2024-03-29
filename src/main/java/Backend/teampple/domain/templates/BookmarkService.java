package Backend.teampple.domain.templates;

import Backend.teampple.domain.templates.dto.request.PostBookmarkDto;
import Backend.teampple.domain.templates.entity.Bookmark;
import Backend.teampple.domain.templates.entity.Template;
import Backend.teampple.domain.templates.repository.BookmarkRepository;
import Backend.teampple.domain.templates.repository.TemplateRespository;
import Backend.teampple.domain.users.entity.User;
import Backend.teampple.domain.users.repository.UserRepository;
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

    private final UserRepository userRepository;

    @Transactional
    public void postBookmark(User authUser, PostBookmarkDto postBookmarkDto) {
        // 1. template 조회
        Template template = templateRespository.findById(postBookmarkDto.getId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.TEMPLATE_NOT_FOUND));

        // 2. bookmark 생성
        Bookmark bookmark = bookmarkRepository.findByUserAndTemplate(authUser, template);
        if (bookmark == null) {
            Bookmark newBookmark = Bookmark.builder()
                    .user(authUser)
                    .template(template)
                    .build();
            bookmarkRepository.save(newBookmark);
        } else {
            bookmarkRepository.delete(bookmark);
        }
    }
}
