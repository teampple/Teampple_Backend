package Backend.teampple.domain.files.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class GetFileBriefDto {

    @NotNull
    @ApiModelProperty(notes = "파일 갯수", example = "12", required = true)
    private int fileNum;

    @NotNull
    @ApiModelProperty(notes = "파일 크기", example = "12345", required = true)
    private int totalSize;

    @Builder
    public GetFileBriefDto(int fileNum, int totalSize) {
        this.fileNum = fileNum;
        this.totalSize = totalSize;
    }
}
