package Backend.teampple.domain.tasks;

import Backend.teampple.domain.files.dto.FileInfoDto;
import Backend.teampple.domain.tasks.dto.response.GetTaskDto;
import Backend.teampple.global.common.response.CommonResponse;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}
