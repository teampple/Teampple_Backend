package Backend.teampple.domain.tasks;

import Backend.teampple.domain.feedbacks.entity.Feedback;
import Backend.teampple.domain.feedbacks.entity.FeedbackOwner;
import Backend.teampple.domain.feedbacks.repository.FeedbackOwnerRespository;
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
import Backend.teampple.global.common.validation.CheckUser;
import Backend.teampple.global.common.validation.dto.UserStageDto;
import Backend.teampple.global.common.validation.dto.UserTaskDto;
import Backend.teampple.global.error.ErrorCode;
import Backend.teampple.global.error.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private final StagesRepository stagesRepository;

    private final FeedbackOwnerRespository feedbackOwnerRespository;

    private final UserRepository userRepository;

    private final CheckUser checkUser;

    @Transactional
    public GetTaskDto getTask(String authUser, Long taskId) {
        // 1. task 조회 및 유저 관한 확인
        UserTaskDto userTaskDto = checkUser.checkIsUserHaveAuthForTask(authUser, taskId);
        Task task = userTaskDto.getTask();
        User user = userTaskDto.getUser();

        // 2. file 조회
        List<GetFileInfoDto> getFileInfoDtos = filesRepository.findAllByTaskOrderByUpdatedAt(task).stream()
                .map(GetFileInfoDto::new).collect(Collectors.toList());

        // 3. operator 조회 유저 프로파일 패치조인
        List<String> operators = operatorRepository.findAllByTaskWithUserProfile(task).stream()
                .map(o -> o.getUserProfile().getName()).collect(Collectors.toList());

        // 4. feedback + adviser + adviserProfile
        List<Feedback> feedbacks = feedbackRepository.findByTaskWithUserAndUserProfile(task);
        List<GetFeedbackDto> getFeedbackDtos = feedbacks.stream()
                .map(GetFeedbackDto::new).collect(Collectors.toList());

        // 5. feedbackOwner ischecked update
        List<FeedbackOwner> feedbackOwners = feedbackOwnerRespository.findAllByUserAndFeedback(user, feedbacks);
        feedbackOwners.stream()
                .filter(feedbackOwner -> !feedbackOwner.isChecked())
                .forEach(FeedbackOwner::updateCheckStatus);

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
    public void postTask(String authUser, TaskDto taskDto, Long stageId) {
        // 1. stage + user + 유저 검증
        UserStageDto userStageDto = checkUser.checkIsUserCanPostTask(authUser, stageId);

        // 2. Task 생성
        Task task = Task.builder()
                .name(taskDto.getName())
                .startDate(taskDto.getStartDate())
                .dueDate(taskDto.getDueDate())
                .stage(userStageDto.getStage())
                .build();
        tasksRepository.save(task);

        // 3. operator user 불러오기
        List<User> users = new ArrayList<>();
        taskDto.getOperators()
                .forEach(opId -> {
                    User user = userRepository.findByIdWithUserProfile(opId)
                            .orElseThrow(() -> new NotFoundException(ErrorCode.INVALID_TEAMMATE.getMessage()));
                    users.add(user);
                });


        // 4. operator 생성
        users.forEach(user -> {
            Operator operator = Operator.builder()
                    .task(task)
                    .user(user)
                    .userProfile(user.getUserProfile())
                    .build();
            operatorRepository.save(operator);
        });
    }

    @Transactional
    public void putTask(String authUser, TaskDto taskDto, Long taskId) {
        // 1. task 조회 및 유저 관한 확인
        Task task = checkUser.checkIsUserHaveAuthForTask(authUser, taskId).getTask();

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
                User user = userRepository.findById(operatorId.get(i))
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

    public void getConvertStatus(String authUser, Long taskId) {
        // 1. task 조회 및 유저 관한 확인
        Task task = checkUser.checkIsUserHaveAuthForTask(authUser, taskId).getTask();

        // 2. task status convert
        task.convertStatus();
        tasksRepository.save(task);
    }
}
