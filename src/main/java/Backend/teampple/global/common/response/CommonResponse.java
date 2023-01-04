package Backend.teampple.global.common.response;

import Backend.teampple.global.error.ErrorCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * api 공통 응답 형식입니다.
 */
@AllArgsConstructor
@Getter
@Builder
public class CommonResponse <T> {
    @JsonProperty("status")
    private int code;

    @JsonProperty("isSuccess")
    private boolean success;

    @JsonProperty("message")
    private String message;

    @JsonProperty("data")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public static <T> CommonResponse<T> onSuccess(int code, T data) {
        return CommonResponse.<T>builder()
                .code(code)
                .success(true)
                .message("요청에 성공하였습니다.")
                .data(data)
                .build();
    }

    public static <T> CommonResponse<T> onFailure(ErrorCode errorCode, String message) {
        return CommonResponse.<T>builder()
                .code(errorCode.getHttpStatus().value())
                .success(false)
                .message(message)
                .data(null)
                .build();
    }
}
