package Backend.teampple.domain.tasks;

import Backend.teampple.domain.feedbacks.FeedbackRepository;
import Backend.teampple.domain.feedbacks.dto.FeedbackDto;
import Backend.teampple.domain.files.FilesRepository;
import Backend.teampple.domain.files.dto.FileInfoDto;
import Backend.teampple.domain.tasks.dto.response.GetTaskDto;
import Backend.teampple.domain.tasks.entity.Task;
import Backend.teampple.global.error.ErrorCode;
import Backend.teampple.global.error.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TasksService {

    private final TasksRepository tasksRepository;

    private final FilesRepository filesRepository;

    private final OperatorRepository operatorRepository;

    private final FeedbackRepository feedbackRepository;

    public GetTaskDto getTask(Long taskId) {

        // 1. task 조회 stage 패치조인
        Task task = tasksRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.TASK_NOT_FOUND.getMessage()));

        // 2. file 조회
        List<FileInfoDto> fileInfoDtos = filesRepository.findAllByTaskOrderByUpdatedAt(task).stream()
                .map(o -> new FileInfoDto(o)).collect(Collectors.toList());

        // 3. operator 조회 유저 프로파일 패치조인
        List<String> operators = operatorRepository.findAllByTask(task).stream()
                .map(o -> o.getUserProfile().getName()).collect(Collectors.toList());

        // 4. feedback 조회 유저, 유저 프로파일 패치조인
        List<FeedbackDto> feedbackDtos = feedbackRepository.findByTask(task).stream()
                .map(o -> new FeedbackDto(o)).collect(Collectors.toList());

        return GetTaskDto.builder()
                .taskName(task.getName())
                .isDone(task.isDone())
                .startDate(task.getStartDate())
                .dueDate(task.getDueDate())
                .stageName(task.getStage().getTaskName())
                .sequenceNum(task.getStage().getSequenceNum())
                .operators(operators)
                .files(fileInfoDtos)
                .feedbacks(feedbackDtos)
                .build();

    };
}
