package Backend.teampple.global.error;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class ErrorResponse {
    private final String timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);

    private final boolean success = false;

    private String code;
    
    private int status;

    private String message;


    @Builder
    public ErrorResponse(int status, String message, String code) {
        this.status = status;
        this.message = message;
        this.code = code;
    }

    public static ErrorResponse onFailure(ErrorCode errorCode) {
        return ErrorResponse.builder()
                .status(errorCode.getHttpStatus().value())
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();
    }
}
