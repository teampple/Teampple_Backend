package Backend.teampple.domain.files.dto;

import Backend.teampple.domain.files.entity.File;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class FileInfoDto {

    @NotNull
    @ApiModelProperty(notes = "이름", example = "teampple", required = true)
    private String filename;

    @NotNull
    @ApiModelProperty(notes = "파일 저장 url", example = "aws.s3....", required = true)
    private String url;

    @NotNull
    @ApiModelProperty(notes = "파일 크기", example = "12345", required = true)
    private Long size;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    @ApiModelProperty(notes = "파일 마지막 수정일", example = "2023-01-01T11:22:33", required = true)
    private LocalDateTime updatedAt;

    @Builder
    public FileInfoDto(String filename, String url, Long size, LocalDateTime updatedAt) {
        this.filename = filename;
        this.url = url;
        this.size = size;
        this.updatedAt = updatedAt;
    }

    public FileInfoDto(File file) {
        this.filename = file.getFilename();
        this.url = file.getUrl();
        this.size = file.getSize();
        this.updatedAt = file.getUpdatedAt();
    }
}
