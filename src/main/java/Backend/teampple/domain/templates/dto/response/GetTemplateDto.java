package Backend.teampple.domain.templates.dto.response;


import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class GetTemplateDto {

    @ApiModelProperty(value = "팀플 이름", example = "teampple", required = true)
    private String name;

    @ApiModelProperty(value = "팀플 이름", example = "teampple", required = true)
    private String url;

    @ApiModelProperty(value = "팀플 이름", example = "teampple", required = true)
    private boolean bookmarked;

    @Builder
    public GetTemplateDto(String name, String url, boolean bookmarked) {
        this.name = name;
        this.url = url;
        this.bookmarked = bookmarked;
    }
}
