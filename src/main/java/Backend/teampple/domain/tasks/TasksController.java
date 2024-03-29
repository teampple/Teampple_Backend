package Backend.teampple.domain.tasks;

import Backend.teampple.domain.tasks.dto.TaskDto;
import Backend.teampple.domain.tasks.dto.response.GetTaskDto;
import Backend.teampple.domain.users.entity.User;
import Backend.teampple.global.common.auth.AuthUser;
import Backend.teampple.global.common.response.CommonResponse;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/tasks")
@Api(tags = "할 일")
public class TasksController {

    private final TasksService tasksService;

    @GetMapping(value = "")
    @Operation(summary = "할 일 조회", description = "할 일 조회 API 입니다.")
    public GetTaskDto getTask(@AuthUser User authUser,
                                              @RequestParam("taskId") Long taskId) {
        log.info("[api-get] 할 일 조회");
        log.info("{}", authUser);

        return tasksService.getTask(authUser, taskId);
    }

    @PostMapping(value = "")
    @Operation(summary = "할 일 추가", description = "할 일 추가 API 입니다.")
    public void postTask(@AuthUser User authUser,
                                           @Valid @RequestBody TaskDto taskDto,
                                           @RequestParam("stageId") Long stageId) {
        log.info("[api-post] 할 일 추가");
        log.info("{}", authUser);

        tasksService.postTask(authUser, taskDto, stageId);
    }

    @PutMapping(value = "")
    @Operation(summary = "할 일 수정", description = "할 일 수정 API 입니다.")
    public void putTask(@AuthUser User authUser,
                                          @Valid @RequestBody TaskDto taskDto,
                                          @RequestParam("taskId") Long taskId) {
        log.info("[api-put] 할 일 수정");
        log.info("{}", authUser);

        tasksService.putTask(authUser, taskDto, taskId);
    }

    @DeleteMapping(value = "")
    @Operation(summary = "할 일 삭제", description = "할 일 삭제 API 입니다.")
    public void deleteTask(@AuthUser User authUser,
                                             @RequestParam("taskId") Long taskId) {
        log.info("[api-delete] 할 일 삭제");
        log.info("{}", authUser);

        tasksService.deleteTask(authUser, taskId);
    }

    @PostMapping(value = "/status")
    @Operation(summary = "할 일 완료 여부 변경", description = "할 일 완료 여부 변경 API 입니다.")
    public void getConvertStatus(@AuthUser User authUser,
                                                   @RequestParam("taskId") Long taskId) {
        log.info("[api-get] 할 일 완료 여부 변경");
        log.info("{}", authUser);

        tasksService.getConvertStatus(authUser, taskId);
    }
}
