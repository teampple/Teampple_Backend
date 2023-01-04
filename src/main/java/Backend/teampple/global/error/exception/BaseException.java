package Backend.teampple.global.error.exception;

import Backend.teampple.global.error.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 비즈니스 로직 예외 처리용 부모 객체입니다.
 */
@Getter
@RequiredArgsConstructor
public class BaseException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String message;
}
