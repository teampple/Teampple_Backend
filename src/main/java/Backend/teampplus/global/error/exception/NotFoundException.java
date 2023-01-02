package Backend.teampplus.global.error.exception;

import Backend.teampplus.global.error.ErrorCode;
import lombok.Getter;

/**
 * status : 404
 */
@Getter
public class NotFoundException extends BaseException {
    public NotFoundException() {
        super(ErrorCode._BAD_REQUEST, ErrorCode._BAD_REQUEST.getMessage());
    }
    public NotFoundException(String message) {
        super(ErrorCode._BAD_REQUEST, message);
    }
}
