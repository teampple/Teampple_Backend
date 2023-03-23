package Backend.teampple.global.error.exception;

import Backend.teampple.global.error.ErrorCode;
import lombok.Getter;

/**
 * status : 403
 */
@Getter
public class ForbiddenException extends BaseException {
    public ForbiddenException() {
        super(ErrorCode.UNAUTHORIZED_ACCESS, ErrorCode.UNAUTHORIZED_ACCESS.getMessage());
    }
    public ForbiddenException(ErrorCode errorCode) {
        super(errorCode, errorCode.getMessage());}
    public ForbiddenException(String message) {
        super(ErrorCode.UNAUTHORIZED_ACCESS, message);
    }
}
