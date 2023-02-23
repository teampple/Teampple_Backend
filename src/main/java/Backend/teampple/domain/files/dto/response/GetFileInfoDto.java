package Backend.teampple.domain.files.dto.response;

import Backend.teampple.domain.files.entity.File;
import Backend.teampple.domain.files.vo.FileInfoVo;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@EqualsAndHashCode
public class GetFileInfoDto {
    @JsonUnwrapped
    private FileInfoVo fileInfoVo;

    public static GetFileInfoDto of(File file) {
        return GetFileInfoDto.builder()
                .fileInfoVo(FileInfoVo.of(file))
                .build();
    }
}
