package Backend.teampplus.config;

import Backend.teampplus.common.exception.ErrorCode;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;

import java.util.Arrays;
import java.util.List;

@Getter
@Builder
public class ErrorResponse <T>{
    private final boolean success = false;
    private int status;
    private String message;
    private List<FieldError> errors;
    private String code;

    public static ErrorResponse of(ErrorCode errorCode) {
        return ErrorResponse.builder()
                .message(errorCode.getMessage())
                .status(errorCode.getHttpStatus().value())
                .errors(Arrays.asList())
                .code(errorCode.getHttpStatus().name())
                .build();
    }

    @Getter
    @NoArgsConstructor
    public static class FieldError {
        private String field;
        private String value;
        private String reason;
    }
}
