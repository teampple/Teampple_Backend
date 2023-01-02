package Backend.teampplus.common.error.exception;

import Backend.teampplus.common.error.ErrorCode;
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
