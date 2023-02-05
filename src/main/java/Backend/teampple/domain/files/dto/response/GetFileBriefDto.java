package Backend.teampple.domain.files.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class GetFileBriefDto {
    @ApiModelProperty(notes = "파일 갯수", example = "12", required = true)
    private int fileNum;

    @ApiModelProperty(notes = "파일 크기", example = "12345", required = true)
    private int totalSize;

    @Builder
    public GetFileBriefDto(int fileNum, int totalSize) {
        this.fileNum = fileNum;
        this.totalSize = totalSize;
    }
}
