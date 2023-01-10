package Backend.teampple.domain.files.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class PostFileDto {

    @NotNull
    @ApiModelProperty(notes = "파일 이름", example = "naemobaji", required = true)
    private String fileName;

    @NotNull
    @ApiModelProperty(notes = "파일 url", example = "spongebob", required = true)
    private String url;

    @NotNull
    @Positive
    @ApiModelProperty(notes = "파일 갯수", example = "12", required = true)
    private Long size;
}
