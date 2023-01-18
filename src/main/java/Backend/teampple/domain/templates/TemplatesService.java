package Backend.teampple.domain.templates;

import Backend.teampple.domain.templates.dto.response.GetTemplateDto;
import Backend.teampple.domain.templates.entity.Bookmark;
import Backend.teampple.domain.templates.entity.Template;
import Backend.teampple.domain.templates.repository.BookmarkRepository;
import Backend.teampple.domain.templates.repository.TemplateRespository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TemplatesService {

    private final TemplateRespository templateRespository;

    private final BookmarkRepository bookmarkRepository;

    @Transactional
    public List<GetTemplateDto> getTemplate() {
        // 1. template 조회
        List<Template> templates = templateRespository.findAll();

        // 2. bookmark 조회
//        List<Bookmark> bookmarks = bookmarkRepository.findAllByUserOrderByTemplateId();

        // 3. template에 bookmark 표시
//        int bmIndex = 0;
//        List<GetTemplateDto> getTemplateDtos = new ArrayList<>();
//        for (Template template : templates) {
//            GetTemplateDto getTemplateDto;
//            if (template.getId().equals(bookmarks.get(bmIndex).getTemplate().getId())) {
//                getTemplateDto = GetTemplateDto.builder()
//                        .name(template.getName())
//                        .url(template.getUrls())
//                        .bookmarked(true)
//                        .build();
//            } else {
//                getTemplateDto = GetTemplateDto.builder()
//                        .name(template.getName())
//                        .url(template.getUrls())
//                        .bookmarked(false)
//                        .build();
//            }
//            getTemplateDtos.add(getTemplateDto);
//        }
//
//        return getTemplateDtos;
        return new ArrayList<>();
    }
}
