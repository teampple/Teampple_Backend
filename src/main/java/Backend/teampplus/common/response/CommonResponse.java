package Backend.teampplus.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * api 공통 응답 형식입니다.
 */
@AllArgsConstructor
@Getter
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
        return new CommonResponse<>(code, true, "요청에 성공하였습니다.", data);
    }

    public static <T> CommonResponse<T> onFailure(int code, String message, T data) {
        return new CommonResponse<>(code, false, message, data);
    }
}
