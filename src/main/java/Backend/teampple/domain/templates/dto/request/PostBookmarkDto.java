package Backend.teampple.domain.templates.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class PostBookmarkDto {
    @NotNull
    @ApiModelProperty(value = "탬플릿 ID", example = "1", required = true)
    private Long id;
}
