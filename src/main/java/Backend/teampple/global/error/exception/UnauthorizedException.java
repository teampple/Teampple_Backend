package Backend.teampple.global.error.exception;

import Backend.teampple.global.error.ErrorCode;
import lombok.Getter;

/**
 * status : 401
 */
@Getter
public class UnauthorizedException extends BaseException {
    public UnauthorizedException() {
        super(ErrorCode._UNAUTHORIZED, ErrorCode._UNAUTHORIZED.getMessage());
    }
    public UnauthorizedException(String message) {
        super(ErrorCode._UNAUTHORIZED, message);
    }
}
