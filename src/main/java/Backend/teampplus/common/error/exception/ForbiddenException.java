package Backend.teampplus.common.error.exception;

import Backend.teampplus.common.error.ErrorCode;
import lombok.Getter;

/**
 * status : 403
 */
@Getter
public class ForbiddenException extends BaseException {
    private String message;

    public ForbiddenException(String message) {
        super(ErrorCode._BAD_REQUEST);
        this.message = message;
    }

    public ForbiddenException(ErrorCode errorCode, String message) {
        super(errorCode);
        this.message = message;
    }

    public ForbiddenException(ErrorCode errorCode) {
        super(errorCode);
    }
}
