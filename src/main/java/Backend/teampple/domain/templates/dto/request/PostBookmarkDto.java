package Backend.teampple.domain.templates.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class PostBookmarkDto {
    @NotNull
    @ApiModelProperty(value = "탬플릿 ID", example = "1", required = true)
    private Long id;
}
