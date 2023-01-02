package Backend.teampplus.common.error;

import Backend.teampplus.common.error.exception.BaseException;
import Backend.teampplus.common.response.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(BaseException.class)
    protected ResponseEntity<CommonResponse> handleBusinessException(BaseException baseException) {
        log.error("handleBusinessException", baseException);
        return new ResponseEntity<>(CommonResponse.onFailure(baseException.getErrorCode(), baseException.getMessage()),
                null, baseException.getErrorCode().getHttpStatus());
    }


    @ExceptionHandler(Exception.class)
    protected ResponseEntity<CommonResponse> handleException(Exception exception) {
        log.error("handleException", exception);
        return new ResponseEntity<>(CommonResponse.onFailure(ErrorCode._INTERNAL_SERVER_ERROR, ErrorCode._INTERNAL_SERVER_ERROR.getMessage()),
                null, INTERNAL_SERVER_ERROR);
    }

}
