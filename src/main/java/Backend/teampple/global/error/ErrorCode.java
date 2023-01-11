package Backend.teampple.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // common
    INVALID_INPUT_VALUE(BAD_REQUEST, "C001", " Invalid Input Value"),
    _INTERNAL_SERVER_ERROR(INTERNAL_SERVER_ERROR, "C000", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(BAD_REQUEST, "C001", "잘못된 요청입니다."),
    _UNAUTHORIZED(UNAUTHORIZED, "C002", "권한이 없습니다."),
    _METHOD_NOT_ALLOWED(METHOD_NOT_ALLOWED, "C003", "지원하지 않는 Http Method 입니다."),

    // Team
    TEAM_NOT_FOUND(BAD_REQUEST, "T001", "해당 팀플이 존재하지 않습니다."),

    // Teammate
    TEAMMATE_NOT_FOUND(BAD_REQUEST, "TM001", "해당 팀원이 존재하지 않습니다."),

    // Stage
    STAGE_SEQUENCE_DUPLICATE(BAD_REQUEST, "S001", "단계 순서가 중복되었습니다."),
    STAGE_NOT_FOUND(BAD_REQUEST,"S002","해당 단계가 존재하지 않습니다."),

    // Task
    TASK_NOT_FOUND(BAD_REQUEST, "TK001", "해당 할 일이 존재하지 않습니다."),

    // User
    USER_NOT_FOUND(BAD_REQUEST, "U001", "해당 유저가 존재하지 않습니다."),

    // Feedback
    FEEDBACK_NOT_FOUND(BAD_REQUEST, "U001", "해당 유저가 존재하지 않습니다."),

    // test
    TEST(BAD_REQUEST, "TEST001", "test");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
