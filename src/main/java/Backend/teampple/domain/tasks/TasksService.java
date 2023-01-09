package Backend.teampple.domain.tasks;

import Backend.teampple.domain.feedbacks.repository.FeedbackRepository;
import Backend.teampple.domain.feedbacks.dto.response.GetFeedbackDto;
import Backend.teampple.domain.files.repository.FilesRepository;
import Backend.teampple.domain.files.dto.response.GetFileInfoDto;
import Backend.teampple.domain.stages.repository.StagesRepository;
import Backend.teampple.domain.stages.entity.Stage;
import Backend.teampple.domain.tasks.dto.TaskDto;
import Backend.teampple.domain.tasks.dto.response.GetTaskDto;
import Backend.teampple.domain.tasks.entity.Operator;
import Backend.teampple.domain.tasks.entity.Task;
import Backend.teampple.domain.tasks.repository.OperatorRepository;
import Backend.teampple.domain.tasks.repository.TasksRepository;
import Backend.teampple.domain.users.entity.User;
import Backend.teampple.domain.users.repository.UserRepository;
import Backend.teampple.global.error.ErrorCode;
import Backend.teampple.global.error.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private final StagesRepository stagesRepository;

    private final UserRepository userRepository;

    @Transactional
    public GetTaskDto getTask(Long taskId) {

        // 1. task 조회 stage 패치조인
        Task task = tasksRepository.findByIdWithStage(taskId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.TASK_NOT_FOUND.getMessage()));

        // 2. file 조회
        List<GetFileInfoDto> getFileInfoDtos = filesRepository.findAllByTaskOrderByUpdatedAt(task).stream()
                .map(o -> new GetFileInfoDto(o)).collect(Collectors.toList());

        // 3. operator 조회 유저 프로파일 패치조인
        List<String> operators = operatorRepository.findAllByTask(task).stream()
                .map(o -> o.getUserProfile().getName()).collect(Collectors.toList());

        // 4. feedback 조회 유저, 유저 프로파일 패치조인
        List<GetFeedbackDto> getFeedbackDtos = feedbackRepository.findByTask(task).stream()
                .map(o -> new GetFeedbackDto(o)).collect(Collectors.toList());

        return GetTaskDto.builder()
                .taskName(task.getName())
                .isDone(task.isDone())
                .startDate(task.getStartDate())
                .dueDate(task.getDueDate())
                .stageName(task.getStage().getTaskName())
                .sequenceNum(task.getStage().getSequenceNum())
                .operators(operators)
                .files(getFileInfoDtos)
                .feedbacks(getFeedbackDtos)
                .build();

    };

    @Transactional
    public void postTask(TaskDto taskDto, Long stageId) {
        // 1. stage 찾기
        Stage stage = stagesRepository.findById(stageId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.STAGE_NOT_FOUND.getMessage()));

        // 2. Task 생성
        Task task = Task.builder()
                .name(taskDto.getName())
                .startDate(taskDto.getStartDate())
                .dueDate(taskDto.getDueDate())
                .stage(stage)
                .build();
        tasksRepository.save(task);

        // 3. 유저 정보 가져오기

        // 4. operator 생성
//        postTaskDto.getOperators().forEach(operatorId -> {
//            Operator operator = Operator.builder()
//                    .task(task)
//                    .user()
//                    .userProfile();
//            operatorRepository.save(operator);
//        });


    }

    @Transactional
    public void putTask(TaskDto taskDto, Long taskId) {
        // 1. task 조회
        Task task = tasksRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.TASK_NOT_FOUND.getMessage()));

        // 2. operator 조회
        List<Operator> operators = operatorRepository.findAllByTaskWithUserOrderByUserId(task);

        // 3. operator taskDto 비교후 변경
        List<Long> operatorId = taskDto.getOperators();
        int i = 0, j = 0;
        for (; i < operatorId.size() && j < operators.size(); i++) {
            Long id = operatorId.get(i);
            Long op = operators.get(j).getUser().getId();
            if (id.equals(op)) {
                i++; j++;
                continue;
            }
            if (id > op) {
                operatorRepository.delete(operators.get(j));
                j++;
            }
            if (id < op) {
                Operator operator = Operator.builder()
                        .user(operators.get(j).getUser())
                        .userProfile(operators.get(j).getUserProfile())
                        .task(task)
                        .build();
                operatorRepository.save(operator);
                i++;
            }
        }
        if (operatorId.size() > operators.size()) {
            while (i < operatorId.size()) {
                User user = userRepository.getByIdWithUserProfile(operatorId.get(i))
                        .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND.getMessage()));
                Operator operator = Operator.builder()
                        .user(user)
                        .userProfile(user.getUserProfile())
                        .task(task)
                        .build();
                operatorRepository.save(operator);
                i++;
            }
        } else {
            while (j < operators.size()) {
                operatorRepository.delete(operators.get(j));
                j++;
            }

        }

        // 4. task update
        task.update(taskDto);
        tasksRepository.save(task);
    }
}
