package Backend.teampple.domain.files.dto.response;

import Backend.teampple.domain.files.entity.File;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class GetFileInfoDto {
    @ApiModelProperty(notes = "파일 고유번호", example = "1", required = true)
    private Long fileId;

    @ApiModelProperty(notes = "이름", example = "teampple", required = true)
    private String filename;

    @ApiModelProperty(notes = "파일 저장 url", example = "aws.s3....", required = true)
    private String url;

    @ApiModelProperty(notes = "파일 크기", example = "12345", required = true)
    private Long size;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    @ApiModelProperty(notes = "파일 마지막 수정일", example = "2023-01-01T11:22:33", required = true)
    private LocalDateTime updatedAt;

    @Builder
    public GetFileInfoDto(Long id, String filename, String url, Long size, LocalDateTime updatedAt) {
        this.fileId = id;
        this.filename = filename;
        this.url = url;
        this.size = size;
        this.updatedAt = updatedAt;
    }

    public GetFileInfoDto(File file) {
        this.fileId = file.getId();
        this.filename = file.getFilename();
        this.url = file.getUrl();
        this.size = file.getSize();
        this.updatedAt = file.getUpdatedAt();
    }
}
