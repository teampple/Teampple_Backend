package Backend.teampple.domain.files.vo;

import Backend.teampple.domain.files.entity.File;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class FileInfoVo {
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

    public static FileInfoVo of(File file) {
        return FileInfoVo.builder()
                .fileId(file.getId())
                .filename(file.getFilename())
                .url(file.getUrl())
                .size(file.getSize())
                .updatedAt(file.getUpdatedAt())
                .build();
    }
}
