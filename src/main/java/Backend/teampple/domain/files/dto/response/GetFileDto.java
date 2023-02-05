package Backend.teampple.domain.files.dto.response;

import Backend.teampple.domain.files.entity.File;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class GetFileDto {
    @ApiModelProperty(notes = "할 일 아이디", example = "1", required = true)
    private Long taskId;

    @ApiModelProperty(notes = "파일 아이디", example = "1", required = true)
    private Long fileId;

    @ApiModelProperty(notes = "파일 이름", example = "파1", required = true)
    private String fileName;

    @ApiModelProperty(notes = "파일 경로", example = "협업툴 시장조사", required = true)
    private String route;

    @ApiModelProperty(notes = "파일 업로더", example = "도라에몽", required = true)
    private String uploader;

    @Positive
    @ApiModelProperty(notes = "파일 크기", example = "12345", required = true)
    private Long size;

    @ApiModelProperty(notes = "파일 url", example = "www.naver.com", required = true)
    private String url;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    @ApiModelProperty(notes = "파일 마지막 수정일", example = "2023-01-01T11:22:33", required = true)
    private LocalDateTime updatedAt;

    public GetFileDto(File file) {
        this.taskId = file.getTask().getId();
        this.fileId = file.getId();
        this.fileName = file.getFilename();
        this.route = file.getTask().getName();
        this.uploader = file.getUser().getUserProfile().getName();
        this.size = file.getSize();
        this.url = file.getUrl();
        this.updatedAt = file.getUpdatedAt();
    }
}
