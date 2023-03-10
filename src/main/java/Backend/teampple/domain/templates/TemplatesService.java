package Backend.teampple.domain.templates;

import Backend.teampple.domain.templates.dto.response.GetTemplateDto;
import Backend.teampple.domain.templates.entity.Bookmark;
import Backend.teampple.domain.templates.entity.Template;
import Backend.teampple.domain.templates.repository.BookmarkRepository;
import Backend.teampple.domain.templates.repository.TemplateRespository;
import Backend.teampple.domain.users.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TemplatesService {
    private final TemplateRespository templateRespository;

    private final BookmarkRepository bookmarkRepository;

    @Transactional
    public List<GetTemplateDto> getTemplate(User authUser) {
        // 1. template 조회
        List<Template> templates = templateRespository.findAllOrderById();

        // 2. bookmark 조회
        List<Bookmark> bookmarks = bookmarkRepository.findAllByUserWithTemplateOrderById(authUser);

        // 3. bookmarks 된거부터 dto에 추가
        List<GetTemplateDto> getTemplateDtos = bookmarks.stream()
                .map(GetTemplateDto::new)
                .collect(Collectors.toList());

        List<Long> bookmarkedId = bookmarks.stream()
                .map(bookmark -> bookmark.getTemplate().getId())
                .collect(Collectors.toList());

        // 4. 나머지 template 추가
        for (Template template : templates) {
            if (!bookmarkedId.contains(template.getId())) {
                getTemplateDtos.add(new GetTemplateDto(template));
            }
        }

        return getTemplateDtos;
    }
}
