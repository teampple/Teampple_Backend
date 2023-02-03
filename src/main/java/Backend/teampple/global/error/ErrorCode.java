package Backend.teampple.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    /* Common */
    INVALID_INPUT_VALUE(BAD_REQUEST, "C001", "Invalid Input Value"),
    _INTERNAL_SERVER_ERROR(INTERNAL_SERVER_ERROR, "C000", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(BAD_REQUEST, "C001", "잘못된 요청입니다."),
    _METHOD_NOT_ALLOWED(METHOD_NOT_ALLOWED, "C003", "지원하지 않는 Http Method 입니다."),


    /* Auth */
    UNAUTHORIZED_ACCESS(UNAUTHORIZED, "AUTH000", "권한이 없습니다."),
    EXPIRED_TOKEN(BAD_REQUEST, "AUTH001", "만료된 엑세스 토큰입니다"),
    INVALID_REFRESH_TOKEN(BAD_REQUEST, "AUTH002", "리프레시 토큰이 유효하지 않습니다"),
    MISMATCH_REFRESH_TOKEN(BAD_REQUEST, "AUTH003", "리프레시 토큰의 유저 정보가 일치하지 않습니다"),
    INVALID_AUTH_TOKEN(UNAUTHORIZED, "AUTH004", "권한 정보가 없는 토큰입니다"),
    UNAUTHORIZED_USER(UNAUTHORIZED, "AUTH005", "현재 내 계정 정보가 존재하지 않습니다"),
    REFRESH_TOKEN_NOT_FOUND(NOT_FOUND, "AUTH006", "로그아웃 된 사용자입니다"),
    FORBIDDEN_USER(FORBIDDEN, "AUTH007", "권한이 없는 유저입니다"),
    LOGIN_FAILED(UNAUTHORIZED, "AUTH008", "로그인에 실패했습니다"),
    INVALID_TOKEN(BAD_REQUEST, "AUTH009", "유효하지 않은 토큰입니다"),
    INVALID_PRINCIPAL(BAD_REQUEST, "AUTH010", "인증정보가 존재하지 않습니다"),

    /* OAuth */
    INVALID_PROVIDER(BAD_REQUEST, "OAUTH001", "서비스에서 제공하지 않는 제공자 입니다."),

    /* Kakao */
    KAKAO_CODE_ERROR(BAD_REQUEST, "K001", "유효하지 않은 카카오 코드입니다"),
    KAKAO_TOKEN_ERROR(BAD_REQUEST, "K002", "유효하지 않은 카카오 토큰입니다"),

    /* Team */
    TEAM_NOT_FOUND(BAD_REQUEST, "TEAM400-1", "해당 팀플이 존재하지 않습니다."),
    TEAM_NOT_VALID(BAD_REQUEST, "TEAM400-2", "유효하지 않은 팀플입니다."),

    /* Teammate */
    TEAMMATE_NOT_FOUND(BAD_REQUEST, "TEAMMATE400-1", "해당 팀원이 존재하지 않습니다."),
    INVALID_TEAMMATE(BAD_REQUEST, "TEAMMATE400-2", "유효하지 않은 팀원입니다."),
    TEAMMATE_ALREADY_EXIST(BAD_REQUEST, "TEAMMATE400-3", "이미 존재하는 팀원입니다."),

    /* Stage */
    STAGE_SEQUENCE_DUPLICATE(BAD_REQUEST, "STAGE400-1", "단계 순서가 중복되었습니다."),
    STAGE_NOT_FOUND(BAD_REQUEST, "STAGE400-2", "해당 단계가 존재하지 않습니다."),
    NEED_STAGE(BAD_REQUEST, "STAGE400-3", "최소 하나의 단계가 존재해야합니다."),

    /* Task */
    TASK_NOT_FOUND(BAD_REQUEST, "TASK400-1", "해당 할 일이 존재하지 않습니다."),
    MISMATCH_TASK(BAD_REQUEST, "TASK400-2", "팀에 속하지 않은 할 일 입니다."),
    NOT_TEAMMATE(BAD_REQUEST, "TASK400-3", "팀원이 아닌 유저입니다."),

    /* User */
    USER_NOT_FOUND(BAD_REQUEST, "USER400-1", "해당 유저가 존재하지 않습니다."),
    MISMATCH_TEAM(BAD_REQUEST, "USER400-2", "해당 유저가 속한 팀이 아닙니다."),

    /* Feedback */
    FEEDBACK_NOT_FOUND(BAD_REQUEST, "FEEDBACK400-1", "해당 피드백이 존재하지 않습니다."),


    /* File */
    FILE_NOT_FOUND(BAD_REQUEST, "FILE400-1", "파일이이 존재하지 않습니다."),

    /* Invitation */
    NOT_VALID_INVITATION(BAD_REQUEST, "INVITATION400-1", "유효하지않은 초대장입니다."),


    /* Template */
    TEMPLATE_NOT_FOUND(BAD_REQUEST, "TEMPLATE400-1", "탬플릿이 존재하지 않습니다."),

    /* S3 */
    S3_SERVER_ERROR(BAD_REQUEST, "S3400-1", "Amazon S3가 처리할 수 없는 요청입니다."),
    S3_CONNECTION_ERROR(BAD_REQUEST, "S3400-2", "Amazon S3에 연결할 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
