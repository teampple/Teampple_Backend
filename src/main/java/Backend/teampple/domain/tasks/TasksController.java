package Backend.teampple.domain.tasks;

import Backend.teampple.domain.tasks.dto.TaskDto;
import Backend.teampple.domain.tasks.dto.response.GetTaskDto;
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
    @Operation(summary = "할 일 조회", description = "할 일 조회 API 입니다.\n"
            + "할 일을 조회합니다.")
    public CommonResponse<GetTaskDto> getTask(@RequestParam("taskId") Long taskId) {
        log.info("[api-get] 할 일 조회");

        // 유저 validation 추가해야함

        GetTaskDto getTaskDto = tasksService.getTask(taskId);
        return CommonResponse.onSuccess(HttpStatus.OK.value(), getTaskDto);
    }

    @PostMapping(value = "")
    @Operation(summary = "할 일 추가", description = "할 일 추가 API 입니다.\n"
            + "할 일을 추가합니다.")
    public CommonResponse<String> postTask(@Valid @RequestBody TaskDto taskDto, @RequestParam("stageId") Long stageId) {
        log.info("[api-post] 할 일 추가");

        // 유저 validation 추가해야함

        tasksService.postTask(taskDto, stageId);
        return CommonResponse.onSuccess(HttpStatus.CREATED.value());
    }

    @PutMapping(value = "")
    @Operation(summary = "할 일 추가", description = "할 일 추가 API 입니다.\n"
            + "할 일을 추가합니다.")
    public CommonResponse<String> putTask(@Valid @RequestBody TaskDto taskDto, @RequestParam("taskId") Long taskId) {
        log.info("[api-put] 할 일 수정");

        // 유저 validation 추가해야함

        tasksService.putTask(taskDto, taskId);
        return CommonResponse.onSuccess(HttpStatus.OK.value());
    }
}
