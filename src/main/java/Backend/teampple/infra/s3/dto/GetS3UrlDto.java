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

    @ApiModelProperty(notes = "url", example = "asd-asd", required = true)
    private String key;

    @Builder
    public GetS3UrlDto(String preSignedUrl, String key) {
        this.preSignedUrl = preSignedUrl;
        this.key = key;
    }
}
