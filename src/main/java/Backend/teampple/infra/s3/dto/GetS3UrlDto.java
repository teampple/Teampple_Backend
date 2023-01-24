package Backend.teampple.infra.s3.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.net.URL;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class GetS3UrlDto {
    @ApiModelProperty(notes = "presigned url", example = "https://www.t-ui/index.html#/", required = true)
    private String preSignedUrl;

    @Builder
    public GetS3UrlDto(String preSignedUrl) {
        this.preSignedUrl = preSignedUrl;
    }
}
