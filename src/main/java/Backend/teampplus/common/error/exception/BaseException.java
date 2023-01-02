package Backend.teampplus.common.error.exception;

import Backend.teampplus.common.error.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;

/**
 * 비즈니스 로직 예외 처리용 부모 객체입니다.
 */
@Getter
@RequiredArgsConstructor
public class BaseException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String message;
}
