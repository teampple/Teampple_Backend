package Backend.teampple.global.error;

import Backend.teampple.global.error.exception.BaseException;
import Backend.teampple.global.common.response.CommonResponse;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import springfox.documentation.annotations.ApiIgnore;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ControllerAdvice
@ApiIgnore
@Slf4j
public class GlobalExceptionHandler {

    // 비즈니스 로직 에러 처리
    @ExceptionHandler(BaseException.class)
    protected ResponseEntity<CommonResponse> handleBusinessException(BaseException baseException) {
        log.error("handleBusinessException", baseException);
        return new ResponseEntity<>(CommonResponse.onFailure(baseException.getErrorCode(), baseException.getMessage()),
                null, baseException.getErrorCode().getHttpStatus());
    }

    // @Valid 으로 binding error 시 발생
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<CommonResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException MANVE) {
        log.error("MethodArgumentNotValidException", MANVE);
        return new ResponseEntity<>(CommonResponse.onFailure(ErrorCode._BAD_REQUEST, ErrorCode._BAD_REQUEST.getMessage()),
                null, ErrorCode._BAD_REQUEST.getHttpStatus());
    }

    // 지원하지 않는 http method 호출시 발생하는 에러
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<CommonResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("HttpRequestMethodNotSupportedException", e);
        return new ResponseEntity<>(CommonResponse.onFailure(ErrorCode._METHOD_NOT_ALLOWED, ErrorCode._METHOD_NOT_ALLOWED.getMessage()),
                null, ErrorCode._METHOD_NOT_ALLOWED.getHttpStatus());
    }

    // JSON parse error 일 경우에 발생합니다
    // request 값을 읽을 수 없을 때 발생합니다.
    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<CommonResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("HttpMessageNotReadableException", e);
        return new ResponseEntity<>(CommonResponse.onFailure(ErrorCode._BAD_REQUEST,ErrorCode._BAD_REQUEST.getMessage()),
                null, ErrorCode._BAD_REQUEST.getHttpStatus());
    }

    // 변수 타입이 맞지 않을 때 발생하는 에러입니다.
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<CommonResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.error("MethodArgumentTypeMismatchException", e);
        return new ResponseEntity<>(CommonResponse.onFailure(ErrorCode._BAD_REQUEST,ErrorCode._BAD_REQUEST.getMessage()),
                null, ErrorCode._BAD_REQUEST.getHttpStatus());
    }

    // 변수 타입이 맞지 않을 때 발생하는 에러입니다.
    @ExceptionHandler(EmptyResultDataAccessException.class)
    protected ResponseEntity<CommonResponse> handleEmptyResultDataAccessException(EmptyResultDataAccessException e) {
        log.error("EmptyResultDataAccessException", e);
        return new ResponseEntity<>(CommonResponse.onFailure(ErrorCode._BAD_REQUEST,ErrorCode._BAD_REQUEST.getMessage()),
                null, ErrorCode._BAD_REQUEST.getHttpStatus());
    }

    // The call was transmitted successfully, but Amazon S3 couldn't process it.
    @ExceptionHandler(AmazonServiceException.class)
    protected ResponseEntity<CommonResponse> handleAmazonServiceException(AmazonServiceException e) {
        log.error("AmazonServiceException", e);
        return new ResponseEntity<>(CommonResponse.onFailure(ErrorCode._BAD_REQUEST,ErrorCode.S3_SERVER_ERROR.getMessage()),
                null, ErrorCode._BAD_REQUEST.getHttpStatus());
    }

    // thrown when service could not be contacted for a response, or when client is unable to parse the response from service.
    @ExceptionHandler(SdkClientException.class)
    protected ResponseEntity<CommonResponse> handleSdkClientException(SdkClientException e) {
        log.error("SdkClientException", e);
        return new ResponseEntity<>(CommonResponse.onFailure(ErrorCode._BAD_REQUEST,ErrorCode.S3_CONNECTION_ERROR.getMessage()),
                null, ErrorCode._BAD_REQUEST.getHttpStatus());
    }

    // 위에서 따로 처리하지 않은 에러를 모두 처리해줍니다.
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<CommonResponse> handleException(Exception exception) {
        log.error("handleException", exception);
        return new ResponseEntity<>(CommonResponse.onFailure(ErrorCode._INTERNAL_SERVER_ERROR, ErrorCode._INTERNAL_SERVER_ERROR.getMessage()),
                null, INTERNAL_SERVER_ERROR);
    }

}
