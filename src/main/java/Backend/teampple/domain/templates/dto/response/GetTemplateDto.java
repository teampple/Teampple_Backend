package Backend.teampple.domain.templates.dto.response;


import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class GetTemplateDto {

    @ApiModelProperty(value = "탬플릿 이름", example = "teampple", required = true)
    private String name;

    @ApiModelProperty(value = "탬플릿 url", example = "https://img.danawa.com/prod_img/500000/099/723/img/4723099_1.jpg?shrink=500:500&_v=20200811140211", required = true)
    private String url;

    @ApiModelProperty(value = "탬플릿 북마크 여부", example = "true", required = true)
    private boolean bookmarked;

    @Builder
    public GetTemplateDto(String name, String url, boolean bookmarked) {
        this.name = name;
        this.url = url;
        this.bookmarked = bookmarked;
    }
}
