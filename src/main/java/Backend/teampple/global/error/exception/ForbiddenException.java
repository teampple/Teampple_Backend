package Backend.teampple.global.error.exception;

import Backend.teampple.global.error.ErrorCode;
import lombok.Getter;

/**
 * status : 403
 */
@Getter
public class ForbiddenException extends BaseException {
    public ForbiddenException() {
        super(ErrorCode._BAD_REQUEST, ErrorCode._BAD_REQUEST.getMessage());
    }
    public ForbiddenException(ErrorCode errorCode,String message) {
        super(errorCode, message);
    }
}
