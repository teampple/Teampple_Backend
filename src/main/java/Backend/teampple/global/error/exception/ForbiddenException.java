package Backend.teampple.global.error.exception;

import Backend.teampple.global.error.ErrorCode;
import lombok.Getter;

/**
 * status : 403
 */
@Getter
public class ForbiddenException extends BaseException {
    public ForbiddenException() {
        super(ErrorCode._UNAUTHORIZED, ErrorCode._UNAUTHORIZED.getMessage());
    }
    public ForbiddenException(String message) {
        super(ErrorCode._UNAUTHORIZED, message);
    }
}
