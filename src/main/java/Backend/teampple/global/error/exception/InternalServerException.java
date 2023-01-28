package Backend.teampple.global.error.exception;

import Backend.teampple.global.error.ErrorCode;
import lombok.Getter;

/**
 * status : 500
 */
@Getter
public class InternalServerException extends BaseException {
    public InternalServerException() {
        super(ErrorCode._INTERNAL_SERVER_ERROR, ErrorCode._INTERNAL_SERVER_ERROR.getMessage());
    }
    public InternalServerException(String message) {
        super(ErrorCode._INTERNAL_SERVER_ERROR, message);
    }
}
