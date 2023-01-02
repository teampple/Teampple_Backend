package Backend.teampplus.common.error.exception;


import Backend.teampplus.common.error.ErrorCode;
import lombok.Getter;

/**
 * status : 400
 */
@Getter
public class BadRequestException extends BaseException {
    public BadRequestException() {
        super(ErrorCode._BAD_REQUEST, ErrorCode._BAD_REQUEST.getMessage());
    }
    public BadRequestException(String message) {
        super(ErrorCode._BAD_REQUEST, message);
    }
}
