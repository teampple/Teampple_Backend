package Backend.teampple.domain.files.dto.response;

import Backend.teampple.domain.files.entity.File;
import Backend.teampple.domain.files.vo.FileInfoVo;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Getter
@Builder
@EqualsAndHashCode
public class GetFileDto {
    @ApiModelProperty(notes = "할 일 아이디", example = "1", required = true)
    private Long taskId;

    @JsonUnwrapped
    private FileInfoVo fileInfoVo;

    @ApiModelProperty(notes = "파일 경로", example = "협업툴 시장조사", required = true)
    private String route;

    @ApiModelProperty(notes = "파일 업로더", example = "도라에몽", required = true)
    private String uploader;

    public static GetFileDto of(File file) {
        return GetFileDto.builder()
                .taskId(file.getTask().getId())
                .fileInfoVo(FileInfoVo.of(file))
                .route(file.getTask().getName())
                .uploader(file.getUser().getUserProfile().getName())
                .build();
    }
}
